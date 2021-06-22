package study.webapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.webapp.controller.BookForm;
import study.webapp.domain.item.Book;
import study.webapp.domain.item.Item;
import study.webapp.repository.ItemRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }

    public void updateItem(Long itemId, BookForm form) {

    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity, String author, String isbn) {
        var bookRetrieved = (Book) findOne(itemId);
        bookRetrieved.update(name, price, stockQuantity, author, isbn);
    }
}
