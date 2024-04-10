package shoppingmall.project;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.project.domain.User;
import shoppingmall.project.domain.subdomain.Address;
import shoppingmall.project.domain.subdomain.Tier;
import shoppingmall.project.repository.UserRepository;

/**
 * 테스트용 initData
 */
@Component
@RequiredArgsConstructor
public class InitData {

    private final UserRepository userRepository;

    @EventListener(value = ApplicationReadyEvent.class)
    @Transactional
    public void initUser() {
        Address address = new Address("1","1","1");
        User user = new User("1","1",1,"1@1","1",address, Tier.NORMAL);
        userRepository.save(user);
    }
}
