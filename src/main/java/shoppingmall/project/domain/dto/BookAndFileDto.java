package shoppingmall.project.domain.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.project.domain.UploadFile;

import java.util.ArrayList;
import java.util.List;

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
