package shoppingmall.project.service.api;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.project.additional.web.session.SessionConst;
import shoppingmall.project.domain.User;
import shoppingmall.project.domain.dto.ItemDto;
import shoppingmall.project.domain.item.Item;
import shoppingmall.project.domain.subdomain.Tier;
import shoppingmall.project.repository.ItemRepository;
import shoppingmall.project.repository.MarketRepository;
import shoppingmall.project.repository.UserRepository;
import shoppingmall.project.repository.api.MarketApiRepository;
import shoppingmall.project.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MarketApiService {

    private final MarketApiRepository marketRepository;

    private final ItemRepository itemRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ItemDto> purchaseItem(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        User user1 = user.orElseThrow();
        // 로그인 한 사용자의 아이디를 사용하여 구매된 상품 목록을 가져옵니다.
        List<Item> itemsByUserId = itemRepository.findItemsByUserId(userId);

        //장바구니의 아이템 리스트의 아이디들 반환
        List<Long> purchaseCartItemId = new ArrayList<>();
        for (Item item : itemsByUserId) {
            purchaseCartItemId.add(item.getId());
        }
        Tier userTier = userService.findUserTier(user1.getId());

        return marketRepository.findItemAndFile(purchaseCartItemId, userId);
    }

    @Transactional(readOnly = true)
    public List<ItemDto> shoppingBasket(Long userId) {
        return marketRepository.findItemAndFileRefactor(userId);
    }
}
