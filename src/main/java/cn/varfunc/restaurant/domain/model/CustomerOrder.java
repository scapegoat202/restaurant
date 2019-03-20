package cn.varfunc.restaurant.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "customer_order")
public class CustomerOrder {
    @Id
    @GeneratedValue
    private long id;

    /**
     * Items of this order.
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "customer_order_order_items",
            joinColumns = @JoinColumn(name = "customer_order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "customer_order_item_id", referencedColumnName = "id"))
    private List<OrderItem> orderItems = new ArrayList<>();

    /**
     * Amount of money of this order.<br>
     * precision = 30, scale = 2
     */
    @Column(nullable = false, updatable = false, precision = 30, scale = 2)
    private BigDecimal amount;

    /**
     * the Store this order belongs to.
     */
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, updatable = false)
    private Store store;

    /**
     * Number of the table.
     */
    @Column(nullable = false, updatable = false)
    private Integer tableNumber;

    /**
     * the Customer this order belongs to.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "customerId", nullable = false, updatable = false)
    private Customer customer;

    /**
     * Status of the order
     */
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
