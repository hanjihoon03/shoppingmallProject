package shoppingmall.project.form.itemform;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class FoodForm {

    private String brand;
    private String name;
    private int price;
    private int quantity;
    private MultipartFile attachFile;
}
