package shoppingmall.project.log.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExampleService {

    @Autowired
    private ExampleAspect exampleAspect;

    public void exampleMethod() {
        exampleAspect.exampleMethod();
    }

    public void exampleServiceMethod() {
        exampleAspect.exampleServiceMethod();
    }

    public void exampleControllerMethod() {
        exampleAspect.exampleControllerMethod();
    }

    public void exampleRepositoryMethod() {
        exampleAspect.exampleRepositoryMethod();
    }
}
