package shoppingmall.project.service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shoppingmall.project.additional.web.session.SessionConst;
import shoppingmall.project.domain.Delivery;
import shoppingmall.project.domain.Market;
import shoppingmall.project.domain.User;
import shoppingmall.project.domain.dto.ItemDto;
import shoppingmall.project.domain.item.Item;
import shoppingmall.project.domain.subdomain.DeliveryStatus;
import shoppingmall.project.repository.DeliveryRepository;
import shoppingmall.project.repository.MarketRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MarketService {

    private final MarketRepository marketRepository;
    private final DeliveryRepository deliveryRepository;

    public void addToCart(Long itemId, int quantity, HttpSession session, Item item) {
        session.setAttribute("itemId", itemId);
        session.setAttribute("quantity", quantity);

        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        Delivery delivery = new Delivery(loginUser.getAddress(), DeliveryStatus.WAIT);
        deliveryRepository.save(delivery);

        Market market = new Market(quantity, loginUser, delivery, item);
        //세션에 장바구니로 추가한 아이템정보 추가
        session.setAttribute(SessionConst.SHOPPING_CART,market);
        marketRepository.save(market);
    }
    public List<ItemDto> purchaseItem(HttpSession session) {
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        if (loginUser == null) {
            // 세션에서 로그인 사용자를 가져올 수 없는 경우 처리
            log.error("로그인 사용자를 찾을 수 없습니다.");
            // 예외처리 또는 기본적인 처리 로직을 추가
            return null; // 또는 null을 반환하거나 다른 적절한 처리를 수행합니다.
        }

        // 로그인 한 사용자의 아이디를 사용하여 구매된 상품 목록을 가져옵니다.
        List<Item> itemsByUserId = marketRepository.findItemsByUserId(loginUser.getId());

        //장바구니의 아이템 리스트의 아이디들 반환
        List<Long> purchaseCartItemId = new ArrayList<>();
        for (Item item : itemsByUserId) {
            purchaseCartItemId.add(item.getId());
        }
        //장바구니 리스트의 아이디에 대한 아이템 반환
        return marketRepository.findItemAndFile(purchaseCartItemId);
    }

//    public Map<Long, Integer> findAllMarketItemQuantity() {
//        List<Market> allMarkets = marketRepository.findAll();
//        Map<Long, Integer> marketItemPrices = new HashMap<>();
//
//        for (Market market : allMarkets) {
//            Long itemId = market.getItems().getId();
//            int itemQuantity = market.getOrderQuantity();
//            marketItemPrices.put(itemId, itemQuantity);
//
//            log.info("Item ID: {}, quantity: {}", itemId, itemQuantity);
//        }
//
//        return marketItemPrices;
//    }



//    public List<Market> findAllMarket() {
//        return marketRepository.findAll();
//    }
    public void deleteMarket(Long id) {
        marketRepository.deleteMarketOfItem(id);
    }

    public int purchaseTotalPrice(List<ItemDto> itemDto) {
        int totalPrice = 0;

        for (ItemDto dto : itemDto) {
            totalPrice += dto.getPrice() * dto.getQuantity();
        }

        return totalPrice;
    }
}
