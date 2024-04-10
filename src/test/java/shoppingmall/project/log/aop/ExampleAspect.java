package shoppingmall.project.log.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExampleAspect {
    @Around("shoppingmall.project.log.aop.Pointcuts.allPointAndMvc()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Before invoking method: " + joinPoint.getSignature().toShortString());

        try {
            // Proceed with the method invocation
            Object result = joinPoint.proceed();

            // Log after the method invocation
            System.out.println("After invoking method: " + joinPoint.getSignature().toShortString());

            return result;
        } catch (Exception e) {
            // Log if an exception occurs
            System.err.println("Exception occurred in method: " + joinPoint.getSignature().toShortString());
            throw e;
        }
    }

    // Test method to apply the aspect
    public void exampleMethod() {
        System.out.println("Executing exampleMethod...");
    }

    // Test method to apply the aspect
    public void exampleServiceMethod() {
        System.out.println("Executing exampleServiceMethod...");
    }

    // Test method to apply the aspect
    public void exampleControllerMethod() {
        System.out.println("Executing exampleControllerMethod...");
    }

    // Test method to apply the aspect
    public void exampleRepositoryMethod() {
        System.out.println("Executing exampleRepositoryMethod...");
    }
}
