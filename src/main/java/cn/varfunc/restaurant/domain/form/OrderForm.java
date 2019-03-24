package cn.varfunc.restaurant.domain.form;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderForm {
    private List<OrderItemForm> orderItems;
    private BigDecimal amount;
    private String tableNumber;
    private Long storeId;
    private Long customerId;
    private String orderStatus;
}
