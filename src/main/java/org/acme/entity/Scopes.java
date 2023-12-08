package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.acme.constants.EntityConstants;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "scopes", schema = "enterprise")
public class Scopes extends PanacheEntityBase {

    @Id
    @Column(name = EntityConstants.SCOPE_NAME)
    private String scopeName;
    private String description;

}
