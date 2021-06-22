package study.webapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import study.webapp.domain.item.Book;
import study.webapp.domain.item.Item;
import study.webapp.service.ItemService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {
        var book = Book.createBook(form.getName(),
                form.getPrice(),
                form.getStockQuantity(),
                form.getAuthor(),
                form.getIsbn()
                );
        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String retrieveAll(Model model) {
        var items = itemService.findItems();
        model.addAttribute("items", items);
        return "/items/itemList";
    }

    @GetMapping("/items/{item_id}/edit")
    public String updateItemForm(@PathVariable("item_id") Long itemId, Model model) {
        var book = (Book) itemService.findOne(itemId); // 캐스팅 하는 방법은 좋지않음. 다른방법이 필요

        var form = new BookForm();
        form.setId(book.getId());
        form.setName(book.getName());
        form.setPrice(book.getPrice());
        form.setStockQuantity(book.getStockQuantity());
        form.setAuthor(book.getAuthor());
        form.setIsbn(book.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{item_id}/edit")
    public String updateItemForm(@PathVariable("item_id") Long itemId, @ModelAttribute("form") BookForm form) {
//        // 준영속 엔티티 - JPA가 관리하지 않지만, 식별자(ID)값을 가지고 있는 경우의 엔티티
//        var book = Book.createBook(itemId, // PathVariable값으로 넘어올때는 조작이 가능하므로 현재 로그인된 회원과 id가 일치하는지 확인하는 로직 필요하다.
//                form.getName(),
//                form.getPrice(),
//                form.getStockQuantity(),
//                form.getAuthor(),
//                form.getIsbn());
//
//        itemService.saveItem(book);

        itemService.updateItem(itemId,
                form.getName(), form.getPrice(), form.getStockQuantity(), form.getAuthor(), form.getIsbn());
        return "redirect:/items";
    }
}
