package shoppingmall.project.form.itemform;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookForm {

    private String isbn;
    private String author;
    private String name;

    private int price;
    private int quantity;
}
