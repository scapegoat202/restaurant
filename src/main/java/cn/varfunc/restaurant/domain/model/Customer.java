package cn.varfunc.restaurant.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

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

    //    TODO Improve Customer Class
}
