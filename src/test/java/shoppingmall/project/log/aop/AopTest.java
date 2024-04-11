package shoppingmall.project.log.aop;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import shoppingmall.project.aop.*;
import shoppingmall.project.domain.item.Item;
import shoppingmall.project.repository.ItemRepository;
import shoppingmall.project.service.ItemService;

@Slf4j
@Import(AspectV6Advice.class)
@SpringBootTest
public class AopTest {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;

    @Test
    void aopInfo() {
        log.info("isAopProxy, ItemService={}", AopUtils.isAopProxy(itemService));
        log.info("isAopProxy, ItemRepository={}", AopUtils.isAopProxy(itemRepository));
    }
    @Test
    void success() {
        Item item = new Item("name", 1, 1);
        itemService.saveItem(item);
    }
    @Test
    void exception() {
        Item item = new Item("ex", 1, 1);
        Assertions.assertThatThrownBy(() -> itemService.saveItem(item))
                .isInstanceOf(IllegalStateException.class);
    }
}
