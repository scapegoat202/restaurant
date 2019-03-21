package cn.varfunc.restaurant.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Accessors(chain = true)
public class Store {
    @Id
    @GeneratedValue
    private long id;

    /**
     * Username for login
     */
    private String username;

    /**
     * Password for login
     */
    @JsonIgnore
    private String password;

    /**
     * Store name
     */
    @Column(nullable = false)
    private String name;

    /**
     * Phone Number
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * Store Announcement
     */
    private String announcement;

    /**
     * Working Group of the Store
     */
    @Column(name = "working_group")
    private String workingGroup;

    /**
     * Address of the Store
     */
    @Embedded
    private Address address;

    /**
     * All categories of the Store
     */
    @JsonIgnore
    @OneToMany(mappedBy = "store")
    private List<Category> categories = new ArrayList<>();
}
