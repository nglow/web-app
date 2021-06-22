package study.webapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.webapp.domain.Member;
import study.webapp.domain.Order;
import study.webapp.domain.item.Item;
import study.webapp.repository.OrderSearch;
import study.webapp.service.ItemService;
import study.webapp.service.MemberService;
import study.webapp.service.OrderService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model) {
        var members = memberService.findMembers();
        var items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("member_id") Long memberId,
                        @RequestParam("item_id") Long itemId,
                        @RequestParam("count") int count) {
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        var orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    @PostMapping("/orders/{order_id}/cancel")
    public String cancelOrder(@PathVariable("order_id") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
