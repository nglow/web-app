package study.webapp.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.webapp.domain.Address;
import study.webapp.domain.Member;
import study.webapp.domain.Order;
import study.webapp.domain.OrderStatus;
import study.webapp.domain.item.Book;
import study.webapp.exception.NotEnoughStockException;
import study.webapp.repository.OrderRepository;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("상품 주문")
    public void testOrder() {
        Member member = createMember();

        Book book = createBook();
        int orderCount = 2;

        var orderId = orderService.order(member.getId(), book.getId(), orderCount);
        var orderRetrieved = orderRepository.findOne(orderId);

        assertThat(orderRetrieved.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(orderRetrieved.getOrderItems().size()).isEqualTo(1);
        assertThat(orderRetrieved.getTotalPrice()).isEqualTo(10000 * orderCount);
        assertThat(book.getStockQuantity()).isEqualTo(8);
    }

    @Test
    @DisplayName("주문 취소")
    public void testOrderCancel() {
        Member member = createMember();

        Book book = createBook();
        int orderCount = 2;
        var orderId = orderService.order(member.getId(), book.getId(), orderCount);

        orderService.cancelOrder(orderId);

        var orderRetrieved = orderRepository.findOne(orderId);
        assertThat(orderRetrieved.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(book.getStockQuantity()).isEqualTo(10);
    }

    @Test
    @DisplayName("상품 주문_재고수량초과")
    public void testOrderStockExcess() {
        Member member = createMember();

        Book book = createBook();
        int orderCount = 11;

        assertThrows(NotEnoughStockException.class,
                () -> orderService.order(member.getId(), book.getId(), orderCount));
    }

    private Book createBook() {
        var book = new Book();
        book.setName("시골 JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        var member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "경기", "123-123"));
        em.persist(member);
        return member;
    }

}
