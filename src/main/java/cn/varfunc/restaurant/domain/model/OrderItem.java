package cn.varfunc.restaurant.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Order Item
 */
@Data
@Entity
@Accessors(chain = true)
@Table(name = "customer_order_item")
public class OrderItem {
    @Id
    @GeneratedValue
    private long id;

    /**
     * Commodity of this order item.
     */
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(nullable = false)
    private Commodity commodity;

    /**
     * Amount of number of this order item.
     */
    @Column(nullable = false)
    private long amount;
}
