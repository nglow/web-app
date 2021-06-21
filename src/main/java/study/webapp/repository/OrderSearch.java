package study.webapp.repository;

import lombok.Getter;
import lombok.Setter;
import study.webapp.domain.OrderStatus;

@Getter @Setter
public class OrderSearch {

    private String memberName;

    private OrderStatus orderStatus;
}
