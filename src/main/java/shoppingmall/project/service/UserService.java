package shoppingmall.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.project.additional.log.template.AbstractTemplate;
import shoppingmall.project.additional.log.trace.LogTrace;
import shoppingmall.project.additional.log.trace.TraceId;
import shoppingmall.project.additional.log.trace.TraceStatus;
import shoppingmall.project.domain.User;
import shoppingmall.project.domain.subdomain.Address;
import shoppingmall.project.domain.subdomain.Tier;
import shoppingmall.project.domain.subdomain.UserRole;
import shoppingmall.project.form.UserForm;
import shoppingmall.project.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * 테스트용 메서드
     * @param loginId
     * @return
     */
    @Transactional(readOnly = true)
    public User findByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException(loginId));


    }

    /**
     * 회원 가입시 회원을 저장하는 로직
     * @param userForm signup 폼에서 받아온 데이터
     * @return
     */
    public void createSaveUser(UserForm userForm) {
        Address address = new Address(userForm.getZipcode(), userForm.getCity(), userForm.getStreet());

        User user = new User(
                userForm.getLoginId(),
                userForm.getName(),
                userForm.getAge(),
                userForm.getEmail(),
                userForm.getPassword(),
                address,
                Tier.NORMAL);
        user.encodePassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public boolean existsId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    /**
     * 테스트에 사용되는 서비스 로직
     */
    public void clear() {
        userRepository.deleteAll();
    }


    /**
     * 로그인 로직
     * @return 비밀번호가 틀릴 시 null 반환
     */
    @Transactional(readOnly = true)
    public User login(String id, String password) {
        return userRepository.findByLoginId(id)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        Optional<User> optionalUser = userRepository.findByLoginId(loginId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (!user.getLoginId().equals("admin")) {
                grantedAuthorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));

                return new org.springframework.security.core.userdetails.User(user.getLoginId(), user.getPassword(), grantedAuthorities);
            } else {

                grantedAuthorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
                return new org.springframework.security.core.userdetails.User(user.getLoginId(), user.getPassword(), grantedAuthorities);
            }
        } else {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");
        }

    }

    /**
     * 구매시 구매한 item의 총 가격만큼 누적금액을 더하고 티어를 바꿔주는 로직
     */
    @Transactional(readOnly = true)
    public int addAccumulatedAmount(User user, int totalPrice) {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        User findUser = optionalUser.orElseThrow(null);

        int resultPrice = findUser.addAmount(totalPrice);

        if (findUser.getAccumulatedAmount() <= 1000000) {
        findUser.upgradeTier(findUser.getAccumulatedAmount());
        }

        return resultPrice;
    }

    /**
     * 구매에서 할인에 필요한 user의 tier를 가져오기 위한 로직
     */
    @Transactional(readOnly = true)
    public Tier findUserTier(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        User findUser = user.orElseThrow(null);
        return findUser.getTier();
    }


}
