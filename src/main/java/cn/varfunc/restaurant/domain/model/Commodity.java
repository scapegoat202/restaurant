package cn.varfunc.restaurant.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Accessors(chain = true)
public class Commodity {
    @Id
    @GeneratedValue
    private long id;

    /**
     * Commodity name.
     */
    private String name;

    /**
     * Commodity price<br>
     * precision = 30, scale = 2.
     */
    @Column(nullable = false, precision = 30, scale = 2)
    private BigDecimal price;

    /**
     * Inventory of this commodity.
     */
    private Long inventory;

    /**
     * the Category this commodity belongs to.
     */
    @JsonIgnoreProperties({"commodities"})
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "category_commodity",
            joinColumns = @JoinColumn(name = "commodity_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();

//    /**
//     * Image of this commodity.
//     */
//    @Lob
//    private File image;
}
