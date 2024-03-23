package shoppingmall.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shoppingmall.project.domain.User;
import shoppingmall.project.domain.subdomain.Address;
import shoppingmall.project.form.UserForm;
import shoppingmall.project.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;



    public Long findById(Long id) {
        userRepository.findById(id);
        return id;
    }

    public User createSaveUser(UserForm userForm) {
        Address address = new Address(userForm.getZipcode(), userForm.getCity(), userForm.getStreet());

        User user = new User(userForm.getName(),
                userForm.getAge(),
                address);
        return userRepository.save(user);
    }
    public void clear() {
        userRepository.deleteAll();
    }


}
