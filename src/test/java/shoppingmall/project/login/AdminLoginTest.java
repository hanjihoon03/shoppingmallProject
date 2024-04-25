package shoppingmall.project.login;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import shoppingmall.project.additional.web.session.SessionConst;
import shoppingmall.project.controller.loginController;
import shoppingmall.project.domain.User;
import shoppingmall.project.domain.subdomain.Address;
import shoppingmall.project.domain.subdomain.Tier;
import shoppingmall.project.service.UserService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(loginController.class)
public class AdminLoginTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testAdminPageAccessForAdminUser() throws Exception {
        // 가짜 admin 사용자 생성
        Address address = new Address("1","1","1");
        User adminUser = new User(
                "admin",
                "admin",
                1,
                "1@1",
                "adminPassword",
                address,
                Tier.NORMAL
        );


        // userService.login()이 호출될 때 admin 사용자를 반환하도록 설정
        when(userService.login("admin", "adminPassword")).thenReturn(adminUser);

        // /login 요청을 통해 admin 사용자로 로그인
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("loginId", "admin")
                        .param("password", "adminPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/adminPage"));

        // admin 사용자로 adminPage에 접근하는 요청을 보냄
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/adminPage"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("admin/adminPage"))
                .andExpect(model().attributeExists(SessionConst.ADMIN)); // 세션에 admin 속성이 존재하는지 확인
    }

    @Test
    public void testAdminPageAccessForNonAdminUser() throws Exception {
        // 가짜 일반 사용자 생성
        Address address = new Address("1","1","1");
        User nonAdminUser = new User(
                "user",
                "admin",
                1,
                "1@1",
                "userPassword",
                address,
                Tier.NORMAL
        );

        // userService.login()이 호출될 때 일반 사용자를 반환하도록 설정
        when(userService.login("user", "userPassword")).thenReturn(nonAdminUser);

        // /login 요청을 통해 일반 사용자로 로그인
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("loginId", "user")
                        .param("password", "userPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        // admin 사용자로 adminPage에 접근하는 요청을 보냄
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/adminPage"))
                .andExpect(status().is3xxRedirection()) // 접근이 차단되어 홈페이지로 리다이렉트되는지 확인
                .andExpect(redirectedUrl("/home"));
    }
}
