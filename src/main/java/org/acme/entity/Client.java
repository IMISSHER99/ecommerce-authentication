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
@Table(name = "client", schema = "enterprise")
public class Client extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = EntityConstants.CLIENT_ID)
    private int clientId;
    @Column(name = EntityConstants.CLIENT_NAME)
    private String clientName;
    @Column(name = EntityConstants.CLIENT_SECRET)
    private String clientSecret;
    @Column(name = EntityConstants.GRANT_TYPES)
    private String grantTypes;
    @Column(name = EntityConstants.REDIRECT_URIS)
    private String redirectUris;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = EntityConstants.CLIENT_SCOPES,
            joinColumns = @JoinColumn(name = EntityConstants.CLIENT_ID),
            inverseJoinColumns = @JoinColumn(name = EntityConstants.SCOPE_NAME)
    )
    private Set<Scopes> scopes;

}
