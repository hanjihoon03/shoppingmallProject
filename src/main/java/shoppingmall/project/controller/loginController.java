package shoppingmall.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shoppingmall.project.additional.annotation.Trace;
import shoppingmall.project.additional.log.Trace2;
import shoppingmall.project.additional.log.template.AbstractTemplate;
import shoppingmall.project.additional.log.trace.LogTrace;
import shoppingmall.project.additional.log.trace.TraceStatus;
import shoppingmall.project.additional.web.session.SessionConst;
import shoppingmall.project.domain.User;
import shoppingmall.project.form.LoginForm;
import shoppingmall.project.form.UserForm;
import shoppingmall.project.service.UserService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class loginController {

    private final UserService userService;


    @GetMapping("login")
    public String loginPage(@ModelAttribute("loginForm") LoginForm loginForm) {

        return "login/login";

    }

    @PostMapping("login")
    public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult,
                        HttpServletRequest request,
                        Model model) {

        if (bindingResult.hasErrors()){
            return "login/login";
        }

        User loginUser = userService.login(loginForm.getLoginId(), loginForm.getPassword());

        if (loginUser == null) {
            bindingResult.reject("loginFail", "아이디 혹은 비밀번호가 다릅니다.");
            model.addAttribute("error","아이디 혹은 비밀번호가 다릅니다.");
            return "login/login";
        }
        HttpSession session = request.getSession();

        session.setAttribute(SessionConst.LOGIN_USER,loginUser);


        return "redirect:/";
    }


    @GetMapping("sign-up")
    public String signUp(@ModelAttribute("userForm") UserForm userForm) {
            return "login/sign-up";
    }

    @PostMapping("sign-up")
    public String createUser(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult) {

            if (bindingResult.hasErrors()) {
                return "login/sign-up";
            }
            userService.createSaveUser(userForm);

            return "redirect:/";
    }


    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
    //어드민 페이지 이동
    //물품 추가 및 제거 수정
    @GetMapping("/adminPage")
    public String adminPage() {
        return "/admin/adminPage";
    }

}