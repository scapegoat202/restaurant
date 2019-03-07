package cn.varfunc.restaurant.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
     * Collection of its Commodities.
     */
    @JsonIgnoreProperties({"categories"})
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "category_commodity",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "commodity_id"))
    private List<Commodity> commodities = new ArrayList<>();

    /**
     * the Store it belongs to.
     */
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    @JsonIgnoreProperties({"categories"})
    private Store store;
}
