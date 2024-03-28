package shoppingmall.project.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shoppingmall.project.additional.ArgumentResolver.LoginUserArgumentResolver;
import shoppingmall.project.additional.intercepter.LogInterceptor;
import shoppingmall.project.additional.intercepter.LoginCheckInterceptor;

import java.util.List;

@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginUserArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**","/*.ico","/error");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/","/login","logout",
                        "/css/**","/js/**","/*.ico","/error", "/sign-up","/bookList",
                        "/clothesList","/electronicsList","/foodList","/purchase/**"
                );

    }

}
