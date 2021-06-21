package study.webapp.domain.item;

import lombok.Getter;
import lombok.Setter;
import study.webapp.domain.Category;
import study.webapp.domain.OrderItem;

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

}
