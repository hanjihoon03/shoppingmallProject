package shoppingmall.project.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
public class AspectV5Order {


    @Aspect
    @Order(2)
    public static class LogAspect {
    @Around("shoppingmall.project.aop.Pointcuts.allPoint()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }

    }


    @Aspect
    @Order(1)
    public static class TxAspect {
    @Around("shoppingmall.project.aop.Pointcuts.allPointAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Exception {
        try {
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    }
}
