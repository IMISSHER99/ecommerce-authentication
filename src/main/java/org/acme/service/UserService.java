package org.acme.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.acme.entity.Users;
import org.mindrot.jbcrypt.BCrypt;

@Slf4j
@RequestScoped
public class UserService {


    @Transactional
    public Users registerUser(Users users) {
        log.info("Attempting to persist user, User Name: {}", users.getUsername());
        users.setPassword(BCrypt.hashpw(users.getPassword(), BCrypt.gensalt()));
        users.persist();
        log.info("User Persisted Successfully: User Name: {}", users.getUsername());
        return users;
    }
}
