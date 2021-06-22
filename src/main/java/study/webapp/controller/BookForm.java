package study.webapp.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookForm {

    private Long id;

    // 공통속성
    private String name;
    private int price;
    private int stockQuantity;

    // 개별속성
    private String author;
    private String isbn;
}
