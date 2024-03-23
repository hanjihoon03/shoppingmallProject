package shoppingmall.project.login;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shoppingmall.project.domain.User;
import shoppingmall.project.domain.subdomain.Address;
import shoppingmall.project.form.UserForm;
import shoppingmall.project.repository.UserRepository;
import shoppingmall.project.service.UserService;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
public class LoginTest {

    @Autowired
    UserService userService;


    @BeforeEach
    void beforeEach() {
        userService.clear();
    }
    @Test
    void saveTest() {
        //given
        UserForm userForm = new UserForm("h", 1, "1", "2", "3");
        Address address = new Address(userForm.getZipcode(), userForm.getCity(), userForm.getStreet());

        //when
        User saveUser = userService.createSaveUser(userForm);
        log.info("user={}", saveUser.getId());
        log.info("user={}", saveUser.getAge());
        log.info("user={}", saveUser.getName());
        log.info("user={}", saveUser.getAddress().getCity());
        log.info("user={}", saveUser.getAddress().getStreet());
        log.info("user={}", saveUser.getAddress().getZipcode());

        //then
        assertThat(saveUser.getName()).isEqualTo("h");
        assertThat(saveUser.getAge()).isEqualTo(1);
        assertThat(saveUser.getAddress().getZipcode()).isEqualTo("1");
        assertThat(saveUser.getAddress().getCity()).isEqualTo("2");
        assertThat(saveUser.getAddress().getStreet()).isEqualTo("3");

    }
}
