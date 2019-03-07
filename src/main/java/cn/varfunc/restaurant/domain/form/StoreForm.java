package cn.varfunc.restaurant.domain.form;

import cn.varfunc.restaurant.domain.model.Address;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StoreForm {
    @NotBlank
    private String name;
    private String phoneNumber;
    private String announcement;
    private String workingGroup;
    private Address address;
}
