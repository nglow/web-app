package study.webapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.webapp.domain.Delivery;
import study.webapp.domain.Member;
import study.webapp.domain.Order;
import study.webapp.domain.OrderItem;
import study.webapp.domain.item.Item;
import study.webapp.repository.ItemRepository;
import study.webapp.repository.MemberRepository;
import study.webapp.repository.OrderRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 엔티티 조회
        var member = memberRepository.findOne(memberId);
        var item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        var orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        var order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order); // -> 다른데에서 참조하지않는(like OrderItem)같은 경우에만 cascade로 설정하고 사용해볼만 함

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        var order = orderRepository.findOne(orderId);
        order.cancel();
    }

    /**
     * 주문 검색
     */
//    public List<Order> findOrders(OrderSearch orderSearch) {
//        return orderRepository.findAll(orderSearch);
//    }
}
