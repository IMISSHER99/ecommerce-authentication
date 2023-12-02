package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.acme.constants.EntityConstants;


@Data
@Entity
@Table(name = "addresses", schema = "enterprise")
@EqualsAndHashCode(callSuper = true)
public class Address extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name = EntityConstants.USER_ID)
    private Users users;
    @Column(name = EntityConstants.STREET_ADDRESS)
    private String streetAddress;
    private String city;
    private String state;
    private String country;
    @Column(name = EntityConstants.POSTAL_CODE)
    private String postalCode;
}
