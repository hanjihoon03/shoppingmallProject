package shoppingmall.project.domain.item;

import jakarta.persistence.Entity;
@Entity
public class Book extends Item {
    private String isbn;
    private String author;
}
