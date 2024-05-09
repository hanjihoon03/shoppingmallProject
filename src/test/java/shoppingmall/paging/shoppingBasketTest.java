package shoppingmall.paging;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shoppingmall.project.ProjectApplication;
import shoppingmall.project.domain.Market;
import shoppingmall.project.domain.User;
import shoppingmall.project.domain.dto.ItemDto;
import shoppingmall.project.domain.item.Item;
import shoppingmall.project.domain.subdomain.Address;
import shoppingmall.project.domain.subdomain.Tier;
import shoppingmall.project.repository.ItemRepository;
import shoppingmall.project.repository.MarketRepository;
import shoppingmall.project.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest(classes = ProjectApplication.class)
public class shoppingBasketTest {

    @Autowired
    MarketRepository marketRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ItemRepository itemRepository;

    @Test
    void marketTest() {
        //given
        Address address = new Address("1","1","1");
        User user2 = new User("2","1",1,"6@6","1",address, Tier.NORMAL);
        userRepository.save(user2);
        Optional<User> user = userRepository.findById(1L);
        User user1 = user.orElseThrow();
        Optional<Item> find1 = itemRepository.findById(1L);
        Item item = find1.orElseThrow();
        Optional<Item> find2 = itemRepository.findById(2L);
        Item item1 = find2.orElseThrow();

        Market market = new Market(1,user1,item);
        Market market1 = new Market(1,user1,item1);
        Market market2 = new Market(1,user2,item1);
        marketRepository.save(market);
        marketRepository.save(market1);
        marketRepository.save(market2);

        List<Long> itemIds = new ArrayList<>();
        itemIds.add(item.getId());
        itemIds.add(item1.getId());

        List<Long> itemIds1 = new ArrayList<>();
        itemIds1.add(item1.getId());


        //when
        List<ItemDto> itemAndFile = marketRepository.findItemAndFile(itemIds, user1.getId());

        List<ItemDto> itemAndFile1 = marketRepository.findItemAndFile(itemIds1, user2.getId());


        //then
        Assertions.assertThat(itemAndFile.size()).isEqualTo(2);
        Assertions.assertThat(itemAndFile1.size()).isEqualTo(1);
    }
}
