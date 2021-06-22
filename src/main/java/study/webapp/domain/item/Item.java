package study.webapp.domain.item;

import lombok.Getter;
import lombok.Setter;
import study.webapp.domain.Category;
import study.webapp.domain.OrderItem;
import study.webapp.exception.NotEnoughStockException;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories;

    private String name;

    private int price;

    private int stockQuantity;

    //==비즈니스 로직==//
    /**
     * Stock 증가
     * @param quantity
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * Stock 감소
     * @param quantity
     */
    public void removeStock(int quantity) {
        var stockRemained = this.stockQuantity - quantity;
        if (stockRemained < 0) {
            throw new NotEnoughStockException("Need more stock");
        }
        this.stockQuantity = stockRemained;
    }

}
