package cn.varfunc.restaurant.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Embeddable;

@Data
@Embeddable
@Accessors(chain = true)
public class Address {
    private String province;
    private String city;
    private String county;
    private String details;
}
