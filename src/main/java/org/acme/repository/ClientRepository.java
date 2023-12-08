package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.RequestScoped;
import org.acme.entity.Client;

import java.util.Optional;

@RequestScoped
public class ClientRepository implements PanacheRepository<Client> {

    /**
     * Get the client based on clientName
     * @param clientName clientName
     * @return Optional Client
     */
    public Optional<Client> findByClientName(String clientName) {
        return find("clientName", clientName).firstResultOptional();
    }
}
