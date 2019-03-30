package cn.varfunc.restaurant.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.UUID;

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
    @Column(unique = true, updatable = false, nullable = false)
    private String username;

    /**
     * Password, this should be the SHA256 hash of the real password.
     */
    @JsonIgnore
    @Column(nullable = false)
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

    @Column(name = "image_uuid")
    private UUID imageUUID;

    @Transient
    private String imageURL;
}
