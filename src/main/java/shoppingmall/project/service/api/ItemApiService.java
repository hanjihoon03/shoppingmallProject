package shoppingmall.project.service.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.project.domain.apidto.ItemApiDto;
import shoppingmall.project.domain.apidto.ItemCond;
import shoppingmall.project.domain.apidto.UpdateItemDto;
import shoppingmall.project.domain.item.Item;
import shoppingmall.project.repository.ItemRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemApiService {

    private final ItemRepository itemRepository;

    public List<ItemApiDto> findAllItem() {
        List<Item> allItem = itemRepository.findAll();

        return allItem.stream()
                .map(item -> {
                    ItemApiDto itemApiDto = new ItemApiDto();
                    itemApiDto.setId(item.getId());
                    itemApiDto.setName(item.getName());
                    itemApiDto.setPrice(item.getPrice());
                    itemApiDto.setQuantity(item.getQuantity());
                    itemApiDto.setDtype(item.getDtype());
                    return itemApiDto;
                        }).toList();
    }
    public List<ItemApiDto> findAllDtype(String dtype) {
        List<Item> allItem = itemRepository.findByDtype(dtype);
        return allItem.stream()
                .map(item -> {
                    ItemApiDto itemApiDto = new ItemApiDto();
                    itemApiDto.setId(item.getId());
                    itemApiDto.setName(item.getName());
                    itemApiDto.setPrice(item.getPrice());
                    itemApiDto.setQuantity(item.getQuantity());
                    itemApiDto.setDtype(item.getDtype());
                    return itemApiDto;
                }).toList();
    }

    public List<ItemApiDto> findPriceRange(int min, int max) {
        ItemCond itemCond = new ItemCond(min, max);
        List<Item> allItem = itemRepository.findPriceRange(itemCond);
        return allItem.stream()
                .map(item -> {
                    ItemApiDto itemApiDto = new ItemApiDto();
                    itemApiDto.setId(item.getId());
                    itemApiDto.setName(item.getName());
                    itemApiDto.setPrice(item.getPrice());
                    itemApiDto.setQuantity(item.getQuantity());
                    itemApiDto.setDtype(item.getDtype());
                    return itemApiDto;
                }).toList();
    }
    public List<ItemApiDto> findDtypePriceRange(String dtype, int min, int max) {
        ItemCond itemCond = new ItemCond(min, max, dtype);
        List<Item> allItem = itemRepository.findDtypePriceRange(itemCond);
        return allItem.stream()
                .map(item -> {
                    ItemApiDto itemApiDto = new ItemApiDto();
                    itemApiDto.setId(item.getId());
                    itemApiDto.setName(item.getName());
                    itemApiDto.setPrice(item.getPrice());
                    itemApiDto.setQuantity(item.getQuantity());
                    itemApiDto.setDtype(item.getDtype());
                    return itemApiDto;
                }).toList();
    }

    public ItemApiDto updateItem(Long id, UpdateItemDto request) {
        Optional<Item> byId = itemRepository.findById(id);
        Item item = byId.orElseThrow(() -> new IllegalArgumentException("not fount:" + id));
        item.updateItem(request.getName(), request.getPrice(), request.getQuantity());

        ItemApiDto itemApiDto = new ItemApiDto();
        itemApiDto.setId(item.getId());
        itemApiDto.setName(item.getName());
        itemApiDto.setPrice(item.getPrice());
        itemApiDto.setQuantity(item.getQuantity());
        itemApiDto.setDtype(item.getDtype());
        return itemApiDto;

    }

    public void deleteByItemId(Long id) {
        itemRepository.deleteById(id);
    }

}
