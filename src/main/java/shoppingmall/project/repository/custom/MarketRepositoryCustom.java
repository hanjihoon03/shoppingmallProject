package shoppingmall.project.repository.custom;

import shoppingmall.project.domain.item.Item;

import java.util.List;

public interface MarketRepositoryCustom {
     List<Item> findItemsByUserId(Long id);
}
