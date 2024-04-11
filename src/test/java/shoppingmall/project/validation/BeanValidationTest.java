package shoppingmall.project.validation;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import shoppingmall.project.form.UserForm;

import java.util.Set;

@Slf4j
public class BeanValidationTest {

    @Test
    void beanValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        UserForm userForm = new UserForm();
        userForm.setName("");
        userForm.setAge(131);
        userForm.setEmail("");
        userForm.setPassword("123");

        Set<ConstraintViolation<UserForm>> validate = validator.validate(userForm);
        for (ConstraintViolation<UserForm> userFormConstraintViolation : validate) {
            log.info("validation={}", userFormConstraintViolation);
            log.info("validation.getmessage={}", userFormConstraintViolation.getMessage());

        }

    }
}
