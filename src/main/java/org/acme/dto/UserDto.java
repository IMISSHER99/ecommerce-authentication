package org.acme.dto;

import lombok.Data;
import org.acme.entity.Users;

@Data
public class UserDto {

    private String username;
    private String password;

}
