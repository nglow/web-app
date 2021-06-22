package study.webapp.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter(value = AccessLevel.PRIVATE)
public class Book extends Item {

    private String author;

    private String isbn;

    public static Book createBook(String name, int price, int stockQuantity, String author, String isbn) {
        var book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        book.setAuthor(author);
        book.setIsbn(isbn);
        return book;
    }

    public static Book createBook(Long id, String name, int price, int stockQuantity, String author, String isbn) {
        var book = new Book();
        book.setId(id);
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        book.setAuthor(author);
        book.setIsbn(isbn);
        return book;
    }

    public void update(String name, int price, int stockQuantity, String author, String isbn) {
        setName(name);
        setPrice(price);
        setStockQuantity(stockQuantity);
        setAuthor(author);
        setIsbn(isbn);
    }
}
