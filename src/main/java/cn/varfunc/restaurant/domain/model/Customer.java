package cn.varfunc.restaurant.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Accessors(chain = true)
public class Customer {
    @Id
    private long id;

    /**
     * Customer name
     */
    private String name;

    /**
     * Gender
     */
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate registerDate;

    private LocalDateTime lastAccessDate;


    /**
     * Email
     */
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<CustomerOrder> orders = new ArrayList<>();


    //    TODO Improve Customer Class
}
