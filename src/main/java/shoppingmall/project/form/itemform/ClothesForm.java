package shoppingmall.project.form.itemform;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.project.domain.item.ClothesType;

@Setter
@Getter
public class ClothesForm {

    private ClothesType clothesType;
    private String brand;
    private int size;

    private String name;
    private int price;
    private int quantity;
    private MultipartFile attachFile;
}
