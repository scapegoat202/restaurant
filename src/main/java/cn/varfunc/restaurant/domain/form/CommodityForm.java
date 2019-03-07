package cn.varfunc.restaurant.domain.form;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CommodityForm {
    private String name;
    private BigDecimal price;
    private Long inventory;
    private List<Long> categories;
}
