package shoppingmall.project.repository.custom;

import shoppingmall.project.domain.dto.ItemDto;
import shoppingmall.project.domain.item.Item;

import java.util.List;

public interface MarketRepositoryCustom {
     List<Item> findItemsByUserId(Long id);
     List<ItemDto> findItemAndFile(List<Long> itemIds);
     void deleteMarketOfItem(Long itemId);
     void deleteMarketOfUser(Long userId);
}
