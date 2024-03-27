package shoppingmall.project.service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shoppingmall.project.additional.web.session.SessionConst;
import shoppingmall.project.domain.Delivery;
import shoppingmall.project.domain.Market;
import shoppingmall.project.domain.User;
import shoppingmall.project.domain.item.Item;
import shoppingmall.project.domain.subdomain.DeliveryStatus;
import shoppingmall.project.repository.DeliveryRepository;
import shoppingmall.project.repository.MarketRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MarketService {

    private final MarketRepository marketRepository;
    private final DeliveryRepository deliveryRepository;

    public void addToCart(Long itemId, int quantity, HttpSession session, Item item) {
        session.setAttribute("itemId", itemId);
        session.setAttribute("quantity", quantity);

        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        Delivery delivery = new Delivery(loginUser.getAddress(), DeliveryStatus.WAIT);
        deliveryRepository.save(delivery);

        Market market = new Market(quantity, loginUser, delivery, item);


        marketRepository.save(market);


    }
    public void purchaseItem(HttpSession session) {
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        List<Market> buyMarket = marketRepository.findAllById(Collections.singleton(loginUser.getId()));
        List<Item> itemsByUserId = marketRepository.findItemsByUserId(loginUser.getId());

    }

    public Map<Long, Integer> findAllMarketItemQuantity() {
        List<Market> allMarkets = marketRepository.findAll();
        Map<Long, Integer> marketItemPrices = new HashMap<>();

        for (Market market : allMarkets) {
            Long itemId = market.getItems().getId();
            int itemQuantity = market.getItems().getQuantity();
            marketItemPrices.put(itemId, itemQuantity);
            log.info("Item ID: {}, Price: {}", itemId, itemQuantity);
        }

        return marketItemPrices;
    }



    public List<Market> findAllMarket() {
        return marketRepository.findAll();
    }
}
