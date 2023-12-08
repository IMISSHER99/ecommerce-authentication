package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.acme.constants.EntityConstants;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;


@Data
@Entity
@Table(name = "users", schema = "enterprise")
@EqualsAndHashCode(callSuper = true)
public class Users extends PanacheEntityBase {

    @Id
    @Column(name = EntityConstants.USER_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Username must be alphanumeric")
    private String username;
    private String password;
    @Email(message = "Invalid email id")
    private String email;
    @Column(name = EntityConstants.IS_ACTIVE, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isActive;
    @Column(name = EntityConstants.IS_ENABLED, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isEnabled;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = EntityConstants.CREATED_AT,  columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt = Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = EntityConstants.UPDATED_AT, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updatedAt = Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Address> addresses;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = EntityConstants.USER_ID),
            inverseJoinColumns = @JoinColumn(name = EntityConstants.ROLE_ID)
    )
    private Set<Role> roles;
}
