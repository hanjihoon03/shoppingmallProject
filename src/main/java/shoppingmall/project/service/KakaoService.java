package shoppingmall.project.service;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import shoppingmall.project.domain.dto.PurchasePayDto;
import shoppingmall.project.pay.KaKaoPayReadyVO;
import shoppingmall.project.pay.KakaoPayApprovalVO;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class KakaoService {

    static final String cid = "TC0ONETIME"; // 가맹점 테스트 코드
    private final MarketService marketService;
    private KakaoPayApprovalVO kakaoPayApprovalVO;
    private KaKaoPayReadyVO kaKaoPayReadyVO;

    @Value("${pay.admin-key}")
    private String adminKey;

    /**
     * user id를 받아 구매 목록을 채우고 카카오페이 결제를 위해 요청 url을 만들어준다.
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public String getReadyParameters(Long id) {
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        PurchasePayDto purchasePayDto = marketService.payRequest(id);

        /** partner_user_id,partner_order_id는 결제 승인 요청에서도 동일해야함 */

        String orderId = "Order" + id;
        // 가맹점 코드 테스트코드는 TC0ONETIME 이다.
        map.add("cid", cid);

        map.add("partner_order_id", orderId);
        map.add("partner_user_id", orderId);

        map.add("item_name", purchasePayDto.getItemName());
        //수량
        map.add("quantity", purchasePayDto.getQuantity());
        //가격
        map.add("total_amount", purchasePayDto.getTotal_price());

        //비과세 금액
        map.add("tax_free_amount", "0");

        // 아래 url은 사용자가 결제 url에서 결제를 성공, 실패, 취소시 나온 결과로 이동할 url을 설정해 주는 것입니다.
        map.add("approval_url", "http://localhost:8080/buyItem"); // 성공 시 redirect url
        map.add("cancel_url", "http://localhost:8080/cancel"); // 취소 시 redirect url
        map.add("fail_url", "http://localhost:8080/fail"); // 실패 시 redirect url

        HttpEntity<MultiValueMap<String, String>> http = new HttpEntity<>(map, this.getHeaders());


        RestTemplate restTemplate = new RestTemplate();

        try {

             kaKaoPayReadyVO = restTemplate.postForObject(new URI("https://kapi.kakao.com/v1/payment/ready"), http, KaKaoPayReadyVO.class);
            return kaKaoPayReadyVO != null ? kaKaoPayReadyVO.getNext_redirect_pc_url() : null;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    public KakaoPayApprovalVO kakaoPayInfo(String pg_token, Long id) {
        PurchasePayDto purchasePayDto = marketService.payRequest(id);
        RestTemplate restTemplate = new RestTemplate();

        String orderId = "Order" + id;
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("cid", cid);
        map.add("tid", kaKaoPayReadyVO.getTid());
        map.add("partner_order_id", orderId);
        map.add("partner_user_id", orderId);
        map.add("pg_token", pg_token);
        map.add("total_amount", purchasePayDto.getTotal_price());

        HttpEntity<MultiValueMap<String, String>> http = new HttpEntity<>(map, this.getHeaders());

        try {
            kakaoPayApprovalVO = restTemplate.postForObject(new URI("https://kapi.kakao.com/v1/payment/approve"), http, KakaoPayApprovalVO.class);
            log.info("" + kakaoPayApprovalVO);

            return kakaoPayApprovalVO;

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }


    }



    /**
     * 헤더 값
     */
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String auth = "KakaoAK " + adminKey;

        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return httpHeaders;
    }


}
