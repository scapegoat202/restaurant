package cn.varfunc.restaurant.domain.form;

import cn.varfunc.restaurant.domain.model.Address;
import lombok.Data;

@Data
public class StoreForm {
    private String name;
    private String phoneNumber;
    private String announcement;
    private String workingGroup;
    private Address address;
    private String username;
    private String password;
    private String imageUUID;
}
