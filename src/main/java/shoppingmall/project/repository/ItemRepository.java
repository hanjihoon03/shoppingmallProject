package shoppingmall.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.item.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
