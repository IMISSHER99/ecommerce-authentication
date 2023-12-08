package org.acme.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.acme.dto.UserDto;
import org.acme.entity.Users;
import org.acme.exceptions.InvalidPasswordException;
import org.acme.exceptions.UserNotFoundException;
import org.acme.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

@Slf4j
@RequestScoped
public class UserService {

    private final UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Register new User
     * @param users User
     * @return User
     */
    @Transactional
    public Users registerUser(Users users) {
        log.info("Attempting to persist user, User Name: {}", users.getUsername());
        users.setPassword(BCrypt.hashpw(users.getPassword(), BCrypt.gensalt()));
        users.persist();
        log.info("User Persisted Successfully: User Name: {}", users.getUsername());
        return users;
    }

    /**
     * Authenticate User
     * @param userDto User
     * @return User
     */
    @Transactional
    public Users authenticateUser(UserDto userDto) {
        log.info("Attempting to authenticate user, User Name: {}", userDto.getUsername());
        Optional<Users> optionalUser = userRepository.findByUsername(userDto.getUsername());
        if(optionalUser.isEmpty()) {
            log.info("User does not exists, User Name: {}", userDto.getUsername());
            throw new UserNotFoundException("User Does not exists");
        }
        if(!BCrypt.checkpw(userDto.getPassword(), optionalUser.get().getPassword())) {
            log.info("Credentials does not match, User Name: {}", userDto.getUsername());
            throw new InvalidPasswordException("Invalid Credentials");
        }
        return optionalUser.get();
    }
}
