package shoppingmall.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.Market;
import shoppingmall.project.repository.custom.MarketRepositoryCustom;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long>, MarketRepositoryCustom {
}
