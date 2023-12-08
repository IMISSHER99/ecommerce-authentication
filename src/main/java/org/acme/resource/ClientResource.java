package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.ClientDto;
import org.acme.entity.Client;
import org.acme.service.ClientService;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Path("/client")
public class ClientResource {

    private final ClientService clientService;

    @Inject
    public ClientResource(ClientService clientService) {
        this.clientService = clientService;
    }


    /**
     * Register new Client
     * @param client Client Pojo
     * @return Client
     */
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerClient(@RequestBody Client client) {
        Client registerClient = clientService.registerClient(client);
        return Response.status(200).entity(registerClient).build();
    }


    /**
     * Authenticate Client
     * @param clientDto Client
     * @return HttpStatus
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateClient(@RequestBody ClientDto clientDto) {
        clientService.authenticateClient(clientDto);
        return Response.status(200).build();
    }
}
