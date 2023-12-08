package org.acme.resource;

import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.inject.Inject;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.acme.dto.ClientDto;
import org.acme.dto.ExceptionFormat;
import org.acme.dto.UserDto;
import org.acme.entity.Users;
import org.acme.exceptions.AccessDeniedException;
import org.acme.service.ClientService;
import org.acme.service.UserService;
import org.acme.utils.AuthenticationUtils;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.time.LocalDateTime;

@Slf4j
@Path("/auth")
public class AuthResource {

    private final ClientService clientService;
    private final UserService userService;
    private final AuthenticationUtils authenticationUtils;

    @Inject
    public AuthResource(ClientService clientService, UserService userService, AuthenticationUtils authenticationUtils) {
        this.clientService = clientService;
        this.userService = userService;
        this.authenticationUtils = authenticationUtils;
    }


    /**
     * Jwt Token Generation Endpoint
     * validates the client and user
     * then generates the token based on the clients role
     * @param userDto User pojo
     * @param authorization base64 encoded client id and secret
     * @return Response
     * @throws Exception AccessDeniedException
     */
    @POST
    @Path("/request")
    public Response authenticate(@RequestBody UserDto userDto, @HeaderParam("Authorization") String authorization) throws Exception {
        String[] extractedCredentials = authenticationUtils.extractCredentials(authorization);
        clientService.authenticateClient(new ClientDto(extractedCredentials));
        Users users = userService.authenticateUser(userDto);
        String token = authenticationUtils.generateAuthorizationCode(users.getRoles(), "amith");
        log.info("TOKEN : {}", token);
        return Response.status(200).build();
    }
}
