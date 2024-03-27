package shoppingmall.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.Delivery;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
