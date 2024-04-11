package shoppingmall.project.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import shoppingmall.project.exception.MyDbException;

@Aspect
@Component
@Slf4j
public class DbExceptionAspect {

    @Around("shoppingmall.project.aop.Pointcuts.allRepository()")
    public Object dbException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("================repository exception check start==================");
            Object result = joinPoint.proceed();

            log.info("================repository exception check end==================");
            return result;
        } catch (MyDbException ex) {
            log.error("repository method exception: {}", ex.getMessage());
            throw ex;
        }
    }
}
