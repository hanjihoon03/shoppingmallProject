package shoppingmall.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.project.domain.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
