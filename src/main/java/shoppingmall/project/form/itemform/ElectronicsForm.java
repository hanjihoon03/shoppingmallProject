package shoppingmall.project.form.itemform;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ElectronicsForm {

    private String brand;
    private String name;
    private int price;
    private int quantity;
    private MultipartFile attachFile;
}
