package cn.varfunc.restaurant.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
     * Description of commodity
     */
    private String description;

    /**
     * Inventory of this commodity.
     */
    private Long inventory;

    /**
     * the Category this commodity belongs to.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinTable(
            name = "category_commodity",
            joinColumns = @JoinColumn(name = "commodity_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Store store;

    @Enumerated(EnumType.STRING)
    private CommodityStatus status;

    @Column(name = "image_uuid")
    private UUID imageUUID;

    @Transient
    private String imageURL;
}
