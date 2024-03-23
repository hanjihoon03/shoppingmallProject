package shoppingmall.project.domain.item;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
public class Book extends Item {
    private String isbn;
    private String author;

    public Book(String name, int price, int quantity, String isbn, String author) {
        super(name, price, quantity);
        this.isbn = isbn;
        this.author = author;
    }
}
