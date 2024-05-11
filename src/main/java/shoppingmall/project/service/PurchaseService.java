package shoppingmall.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.project.domain.Delivery;
import shoppingmall.project.domain.Purchase;
import shoppingmall.project.domain.User;
import shoppingmall.project.domain.dto.ItemDto;
import shoppingmall.project.domain.dto.PurchasePayDto;
import shoppingmall.project.domain.item.Item;
import shoppingmall.project.repository.PurchaseRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    public void addPurchase(ItemDto item, Delivery delivery,User user) {
        Purchase purchase = new Purchase(
                item.getName(),
                item.getPrice(),
                item.getQuantity(),
                user,
                delivery
        );
        purchaseRepository.save(purchase);
    }




}
