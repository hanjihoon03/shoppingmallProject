package shoppingmall.project.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shoppingmall.project.controller.MarketController;
import shoppingmall.project.domain.subdomain.Tier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class MarketServiceTest {
    @Autowired
    MarketService marketService;

    @Test
    void addToCart() {
    }

    @Test
    void purchaseItem() {
    }

    @Test
    void deleteMarketUser() {
    }

    @Test
    void deleteMarketItem() {
    }

    @Test
    void purchaseTotalPrice() {
    }

    @Test
    void test() {

        // given
        int totalPrice = 10000;
        Tier bronzeTier = Tier.BRONZE;
        Tier silverTier = Tier.SILVER;
        Tier goldTier = Tier.GOLD;

        int bronzeDiscount = (int) (totalPrice - (totalPrice * bronzeTier.getValue()));
        log.info("bronzeTier={}", bronzeDiscount);
        int silverDiscount = (int) (totalPrice - (totalPrice * silverTier.getValue()));
        log.info("silverTier={}", silverDiscount);
        int goldDiscount = (int) (totalPrice - (totalPrice * goldTier.getValue()));
        log.info("goldTier={}", goldDiscount);

        // when
        int expectedBronzeDiscount = (int) (bronzeDiscount / (1 - Tier.BRONZE.getValue()));
        log.info("bronze={}", expectedBronzeDiscount);
        int expectedSilverDiscount = (int) (silverDiscount / (1 - Tier.SILVER.getValue()));
        log.info("silver={}", expectedSilverDiscount);
        int expectedGoldDiscount = (int) (goldDiscount / (1 - Tier.GOLD.getValue()));
        log.info("gold={}", expectedGoldDiscount);

        int actualBronzeDiscount = marketService.discountAmount(bronzeDiscount, bronzeTier);
        int actualSilverDiscount = marketService.discountAmount(silverDiscount, silverTier);
        int actualGoldDiscount = marketService.discountAmount(goldDiscount, goldTier);

        log.info("actualBronzeDiscount={}", actualBronzeDiscount);
        log.info("actualSilverDiscount={}", actualSilverDiscount);
        log.info("actualGoldDiscount={}", actualGoldDiscount);

        //then
        assertEquals(expectedBronzeDiscount, actualBronzeDiscount);
        assertEquals(expectedSilverDiscount, actualSilverDiscount);
        assertEquals(expectedGoldDiscount, actualGoldDiscount);

    }
}