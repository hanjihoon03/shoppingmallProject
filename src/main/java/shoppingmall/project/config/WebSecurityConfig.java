package shoppingmall.project.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import shoppingmall.project.service.UserService;

import static org.springframework.security.config.Customizer.withDefaults;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig {
    private final UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                // 주소마다 접근 권한 설정
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                // 특정 주소에 대한 접근 허용
                                .requestMatchers(
                                        "/", "/login/**", "logout", "/images/**", "/home", "/loginHome",
                                        "/css/**", "/js/**", "/*.ico", "/error/**", "/sign-up", "/bookList",
                                        "/admin/**", "/adminPage", "/ioError", "/clothesList", "/electronicsList",
                                        "/foodList", "/purchase/**", "/api/**", "/swagger-ui/**", "/deleteItem/**")
                                .permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN") // 특정 역할이 있는 사용자만 허용
                                .anyRequest().authenticated() // 그 외의 요청은 인증 필요
                )
                // 폼 로그인 설정
                .formLogin((formLogin) ->
                        formLogin
                                .loginPage("/login") // 로그인 페이지 URL
                                .defaultSuccessUrl("/loginHome") // 로그인 성공 시 이동할 URL
                                .permitAll() // 모든 사용자에게 접근 허용
                )
                // 로그아웃 설정
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 로그아웃 요청 URL
                        .logoutSuccessUrl("/") // 로그아웃 성공 시 이동할 URL
                        .invalidateHttpSession(true) // HTTP 세션 무효화

                )
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화
                .build(); // 보안 필터 체인 생성
    }

    // 비밀번호 암호화를 위한 PasswordEncoder 빈 등록
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 사용자 인증 정보를 가져오는데 사용되는 UserDetailsService와 PasswordEncoder를 설정
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
}
