package cn.varfunc.restaurant.domain.form;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class OrderForm {
    private Set<OrderItemForm> orderItems;
    private BigDecimal amount;
    private Integer tableNumber;
    private Long storeId;
    private Long customerId;
    private String orderStatus;
}
