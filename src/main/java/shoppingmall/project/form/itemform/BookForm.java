package shoppingmall.project.form.itemform;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
public class BookForm {

    @NotEmpty
    private String isbn;
    @NotEmpty
    private String author;
    @NotEmpty
    private String name;

    @NotNull
    @Range(min = 1)
    private Integer price;
    @NotNull
    @Range(min = 1)
    private Integer quantity;
    @NotEmpty
    private MultipartFile attachFile;

}
