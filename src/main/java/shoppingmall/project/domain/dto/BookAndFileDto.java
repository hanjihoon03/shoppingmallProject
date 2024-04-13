package shoppingmall.project.domain.dto;


import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


/**
 * 저장한 item과 해당하는 저장된 이미지 파일을 불러오기 위한 dto
 */
@Setter
@Getter
public class BookAndFileDto {

    private Long id;
    private String isbn;
    private String author;
    private String name;

    private int price;
    @Min(value = 1)
    private Integer quantity;
    private String uploadFileName;
    private String storeFileName;
    private MultipartFile attachFile;

    public BookAndFileDto() {
    }

    public BookAndFileDto(Long id, String isbn, String author, String name, int price, Integer quantity, String uploadFileName, String storeFileName) {
        this.id = id;
        this.isbn = isbn;
        this.author = author;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public BookAndFileDto(Long id, String isbn, String author, String name, int price, Integer quantity, String uploadFileName, String storeFileName, MultipartFile attachFile) {
        this.id = id;
        this.isbn = isbn;
        this.author = author;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.attachFile = attachFile;
    }
}
