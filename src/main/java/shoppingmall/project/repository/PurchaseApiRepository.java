package shoppingmall.project.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.Delivery;
import shoppingmall.project.domain.Purchase;
import shoppingmall.project.domain.apidto.UserPurchaseDto;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PurchaseApiRepository {

    private final EntityManager em;

    public List<Delivery> userDeliveryList(Long id) {
        return em.createQuery(
                        "select d from Delivery d where d.user.id = :id", Delivery.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Purchase> userPurchaseList(Long id) {
        return em.createQuery(
                        "select p from Purchase p join fetch p.delivery d where d.user.id = :id", Purchase.class)
                .setParameter("id", id)
                .getResultList();
    }
}
