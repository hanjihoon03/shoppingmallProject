package shoppingmall.project.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserForm {

    private String name;
    private int age;

    private String zipcode;
    private String city;
    private String street;

    public UserForm() {
    }

    public UserForm(String name, int age, String zipcode, String city, String street) {
        this.name = name;
        this.age = age;
        this.zipcode = zipcode;
        this.city = city;
        this.street = street;
    }
}
