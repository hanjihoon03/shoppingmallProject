package shoppingmall.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import shoppingmall.project.domain.User;
import shoppingmall.project.form.UserForm;
import shoppingmall.project.service.UserService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class loginController {

    private final UserService userService;

    @GetMapping("login")
    public String login() {
        return "login/login";
    }

    @GetMapping("sign-up")
    public String signUp(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "login/sign-up";
    }

    @PostMapping("sign-up")
    public String createUser(UserForm userForm) {
        User saveUser = userService.createSaveUser(userForm);
        log.info("Received UserForm data: {}", userForm);
        return "redirect:/";
    }



}
