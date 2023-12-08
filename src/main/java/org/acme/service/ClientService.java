package org.acme.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.acme.dto.ClientDto;
import org.acme.entity.Client;
import org.acme.exceptions.AccessDeniedException;
import org.acme.exceptions.InvalidPasswordException;
import org.acme.exceptions.UserNotFoundException;
import org.acme.repository.ClientRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

@Slf4j
@RequestScoped
public class ClientService {

    private final ClientRepository clientRepository;

    @Inject
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Register new Client
     * @param client Client
     * @return Client
     */
    @Transactional
    public Client registerClient(Client client) {
        log.info("Attempting to persist Client, Client Name: {}", client.getClientName());
        client.setClientSecret(BCrypt.hashpw(client.getClientSecret(), BCrypt.gensalt()));
        client.persist();
        log.info("Client Persisted Successfully: Client Name: {}", client.getClientName());
        return client;
    }

    /**
     * Authenticate Client
     * @param clientDto Client
     */
    public void authenticateClient(ClientDto clientDto) {
        log.info("Attempting to authenticate Client, Client Name: {}", clientDto.getClientName());
        Optional<Client> optionalClient = clientRepository.findByClientName(clientDto.getClientName());
        if (optionalClient.isEmpty()) {
            log.info("Client does not exists, Client Name: {}", clientDto.getClientName());
            throw new AccessDeniedException("Client Authentication Failed Access Denied");
        }
        if (!BCrypt.checkpw(clientDto.getClientSecret(), optionalClient.get().getClientSecret())) {
            log.info("Credentials does not match, Client Name: {}", clientDto.getClientName());
            throw new InvalidPasswordException("Invalid Credentials");
        }
    }
}
