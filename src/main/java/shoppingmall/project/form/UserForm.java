package shoppingmall.project.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class UserForm {
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String name;
    @NotNull
    @Range(min = 1, max = 130)
    private Integer age;
    @Email
    private String email;
    @NotEmpty
    private String password;


    @NotEmpty
    private String zipcode;
    @NotEmpty
    private String city;
    @NotEmpty
    private String street;

    public UserForm() {
    }

    public UserForm(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public UserForm(String name, Integer age, String email, String password, String zipcode, String city, String street) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.zipcode = zipcode;
        this.city = city;
        this.street = street;
    }
}
