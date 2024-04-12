package shoppingmall.project.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import shoppingmall.project.domain.apidto.ItemApiDto;
import shoppingmall.project.domain.apidto.UpdateItemDto;
import shoppingmall.project.domain.item.Item;
import shoppingmall.project.repository.ItemRepository;
import shoppingmall.project.service.api.ItemApiService;

import java.nio.channels.Pipe;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class ApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemApiService itemApiService;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        itemRepository.deleteAll();
    }

    @Test
    void update() throws Exception {
        //given
        final String url = "/api/item/{id}";
        final String name = "name";
        final int price = 10;
        final int quantity = 10;

        Item item = new Item(name,price,quantity);
        itemRepository.save(item);

        final String upName = "new name";
        final int upPrice = 100;
        final int upQuantity = 100;

        UpdateItemDto updateItemDto = new UpdateItemDto(upName, upPrice, upQuantity);

        //when
        ResultActions result = mockMvc.perform(post(url, item.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(updateItemDto)));

        //then
        result.andExpect(status().isOk());

        Item findItem = itemRepository.findById(item.getId()).get();

        assertThat(findItem.getName()).isEqualTo(upName);
        assertThat(findItem.getPrice()).isEqualTo(upPrice);
        assertThat(findItem.getQuantity()).isEqualTo(upQuantity);

    }

    @Test
    void deleteApi() throws Exception {
        //given
        final String url = "/api/item/{id}";
        final String name = "name";
        final int price = 10;
        final int quantity = 10;

        Item item = new Item(name,price,quantity);
        itemRepository.save(item);

        //when
        mockMvc.perform(delete(url, item.getId())).andExpect(status().isOk());

        //then
        List<Item> all = itemRepository.findAll();
        assertThat(all).isEmpty();
    }
}
