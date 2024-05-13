package shoppingmall.project.service.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.project.domain.Delivery;
import shoppingmall.project.domain.Purchase;
import shoppingmall.project.domain.apidto.UserPurchaseDto;
import shoppingmall.project.repository.api.PurchaseApiRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseApiService {

    private final PurchaseApiRepository purchaseApiRepository;

    @Transactional(readOnly = true)
    public List<UserPurchaseDto> purchaseList(Long id) {
    return purchaseApiRepository.purchaseList(id);
    }

}
