package shoppingmall.project.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Aspect
public class TransactionAspect {

    @Around("@annotation(transactional)")
    public Object logTransaction(ProceedingJoinPoint joinPoint, Transactional transactional) throws Throwable {
        String transactionName = transactional.value();
        log.info("Transaction = {} started", transactionName );


        try {
            // Proceed with the method invocation
            Object result = joinPoint.proceed();
            log.info("Transaction = {} completed successfully", transactionName);

            return result;
        } catch (Exception e) {
            log.info("Transaction = {} failed = {}", transactionName, e.getMessage());
            throw e;
        }
    }
}
