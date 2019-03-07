package cn.varfunc.restaurant.domain.form;

import lombok.Data;

@Data
public class OrderItemForm {
    public Long amount;
    private Long commodityId;
}
