package org.acme.resource;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.UserDto;
import org.acme.entity.Users;
import org.acme.service.UserService;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Path("/user")
public class UserResource {

    private final UserService userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }


    /**
     * Register new User
     * @param users User
     * @return User
     */
    @POST
    @Path("/register")
    @PermitAll()
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(@RequestBody Users users) {
        Users registeredUser = userService.registerUser(users);
        return Response.status(200).entity(registeredUser).build();
    }


    /**
     * Authenticate User
     * @param userDto User
     * @return HttpStatus
     */
    @POST
    @Path("/login")
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateUser(@RequestBody UserDto userDto) {
        userService.authenticateUser(userDto);
        return Response.status(200).build();
    }
}
