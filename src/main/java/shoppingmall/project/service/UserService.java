package shoppingmall.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shoppingmall.project.additional.log.template.AbstractTemplate;
import shoppingmall.project.additional.log.trace.LogTrace;
import shoppingmall.project.additional.log.trace.TraceId;
import shoppingmall.project.additional.log.trace.TraceStatus;
import shoppingmall.project.domain.User;
import shoppingmall.project.domain.subdomain.Address;
import shoppingmall.project.domain.subdomain.Tier;
import shoppingmall.project.form.UserForm;
import shoppingmall.project.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;



    public User findByLoginId(String loginId) {
        Optional<User> optionalUser = userRepository.findByLoginId(loginId);
                //호출부에서 null 반환시 처리하는 로직 필요
        return optionalUser.orElse(null);

    }

    public User createSaveUser(UserForm userForm) {
        Address address = new Address(userForm.getZipcode(), userForm.getCity(), userForm.getStreet());

        User user = new User(
                userForm.getLoginId(),
                userForm.getName(),
                userForm.getAge(),
                userForm.getEmail(),
                userForm.getPassword(),
                address,
                Tier.NORMAL);
        return userRepository.save(user);
    }
    public void clear() {
        userRepository.deleteAll();
    }


    /**
     * 로그인 로직
     * @return 비밀번호가 틀릴 시 null 반환
     */
    public User login(String id, String password) {
        return userRepository.findByLoginId(id)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    /**
     * 구매시 구매한 item의 총 가격만큼 누적금액을 더하고 티어를 바꿔주는 로직
     */
    public int addAccumulatedAmount(User user, int totalPrice) {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        User findUser = optionalUser.orElseThrow(null);

        int resultPrice = findUser.addAmount(totalPrice);

        if (findUser.getAccumulatedAmount() <= 1000000) {
        findUser.upgradeTier(findUser.getAccumulatedAmount());
        }

        return resultPrice;
    }
    public Tier findUserTier(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        User findUser = user.orElseThrow(null);
        return findUser.getTier();
    }



}
