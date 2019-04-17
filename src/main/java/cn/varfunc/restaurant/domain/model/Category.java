package cn.varfunc.restaurant.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Entity
@Accessors(chain = true)
public class Category {
    @Id
    @GeneratedValue
    private long id;

    /**
     * Category name.
     */
    @Column(nullable = false)
    private String name;

    /**
     * the Store it belongs to.
     */
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Store store;
}
