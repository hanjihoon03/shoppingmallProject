package shoppingmall.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.project.domain.Delivery;
import shoppingmall.project.domain.Purchase;
import shoppingmall.project.domain.User;
import shoppingmall.project.domain.dto.ItemDto;
import shoppingmall.project.domain.subdomain.DeliveryStatus;
import shoppingmall.project.repository.DeliveryRepository;
import shoppingmall.project.repository.PurchaseRepository;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final PurchaseRepository purchaseRepository;

    public void addDelivery(ItemDto item, User user) {

        Delivery delivery = new Delivery(
                user.getAddress(),
                DeliveryStatus.DELIVERY,
                LocalDateTime.now(),
                user
        );
        Purchase purchase = new Purchase(
                item.getName(),
                item.getPrice(),
                item.getQuantity(),
                delivery
        );
        deliveryRepository.save(delivery);
        purchaseRepository.save(purchase);
    }
}
