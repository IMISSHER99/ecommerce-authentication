package org.acme.dto;

import lombok.Data;
import org.acme.entity.Users;

@Data
public class UserDto {

    private String username;
    private String password;
    private String email;

    public Users convertToUsers() {
        Users users = new Users();
        users.setUsername(username);
        users.setPassword(password);
        users.setEmail(email);
        return users;
    }
}
