package org.acme.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientDto {

    private String clientName;
    private String clientSecret;

    public ClientDto(String[] credentials) {
        setClientName(credentials[0]);
        setClientSecret(credentials[1]);
    }
}
