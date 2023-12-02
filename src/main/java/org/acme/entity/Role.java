package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.acme.constants.EntityConstants;
import org.acme.constants.UserRole;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "role", schema = "enterprise")
@EqualsAndHashCode(callSuper = true)
public class Role extends PanacheEntityBase {
    @Id
    @Column(name = EntityConstants.ROLE_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;
    @Enumerated(EnumType.STRING)
    @Column(name = EntityConstants.ROLE_NAME, nullable = false)
    private UserRole roleName;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = EntityConstants.CREATED_AT, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = EntityConstants.UPDATED_AT, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updatedAt;
}
