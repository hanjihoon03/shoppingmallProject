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
        List<Delivery> deliveryList = purchaseApiRepository.userDeliveryList(id);

        List<UserPurchaseDto> userPurchaseDtos = new ArrayList<>();
        for (Delivery delivery : deliveryList) {
            UserPurchaseDto userPurchaseDto = new UserPurchaseDto(
                    delivery.getId(),
                    delivery.getOrderTime(),
                    delivery.getAddress()
            );
            userPurchaseDtos.add(userPurchaseDto);
        }
        List<Purchase> purchases = purchaseApiRepository.userPurchaseList(id);
        for (Purchase purchase : purchases) {
            for (UserPurchaseDto userPurchaseDto : userPurchaseDtos) {
                userPurchaseDto.setOrderPrice(purchase.getOrderPrice());
                userPurchaseDto.setOrderQuantity(purchase.getOrderQuantity());
                userPurchaseDto.setItemName(purchase.getItemName());
            }
        }
        return userPurchaseDtos;

    }
}
