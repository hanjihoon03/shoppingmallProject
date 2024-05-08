package shoppingmall.project.repository.custom;

import shoppingmall.project.domain.dto.ItemDto;
import shoppingmall.project.domain.dto.MarketPayDto;
import shoppingmall.project.domain.dto.PurchasePayDto;
import shoppingmall.project.domain.item.Item;

import java.util.List;

public interface MarketRepositoryCustom {

     List<ItemDto> findItemAndFile(List<Long> itemIds);
     void deleteMarketOfItem(Long itemId, Long userId);
     void deleteMarketOfUser(Long userId);
     List<MarketPayDto> shoppingBasket(Long id);
}
