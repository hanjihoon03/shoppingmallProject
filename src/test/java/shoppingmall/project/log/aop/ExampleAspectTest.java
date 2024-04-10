package shoppingmall.project.log.aop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ExampleAspectTest {
    @Autowired
    private ExampleService exampleService;

    @Test
    public void testExampleMethod() {
        exampleService.exampleMethod();
    }

    @Test
    public void testExampleServiceMethod() {
        exampleService.exampleServiceMethod();
    }

    @Test
    public void testExampleControllerMethod() {
        exampleService.exampleControllerMethod();
    }

    @Test
    public void testExampleRepositoryMethod() {
        exampleService.exampleRepositoryMethod();
    }
}
