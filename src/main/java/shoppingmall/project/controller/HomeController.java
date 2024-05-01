package shoppingmall.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import shoppingmall.project.additional.annotation.Login;
import shoppingmall.project.domain.User;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("")
    public String home(@Login User loginUser, Model model) {


        if (loginUser == null) {
            return "home";
        }

        model.addAttribute("user", loginUser);
        return "loginHome";
    }
}
