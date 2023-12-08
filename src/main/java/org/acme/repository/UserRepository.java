package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import org.acme.entity.Users;

import java.util.Optional;

@RequestScoped
public class UserRepository implements PanacheRepository<Users> {

    /**
     * Get User based on username
     * @param username username
     * @return Optional User
     */
    public Optional<Users> findByUsername(String username) {
        return find("username", username).firstResultOptional();
    }
}