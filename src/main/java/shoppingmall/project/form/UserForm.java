package shoppingmall.project.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserForm {
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String name;
    @NotEmpty
    private String age;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;


    private String zipcode;

    private String city;

    private String street;

    public UserForm() {
    }

    public UserForm(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public UserForm(String name, String age, String email, String password, String zipcode, String city, String street) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.zipcode = zipcode;
        this.city = city;
        this.street = street;
    }
}
