package shoppingmall.project.domain.dto;

import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;
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
    private int quantity;
    private String uploadFileName;
    private String storeFileName;

    public BookAndFileDto() {
    }

    public BookAndFileDto(Long id, String isbn, String author, String name, int price, int quantity, String uploadFileName, String storeFileName) {
        this.id = id;
        this.isbn = isbn;
        this.author = author;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
