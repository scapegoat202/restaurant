package cn.varfunc.restaurant.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
