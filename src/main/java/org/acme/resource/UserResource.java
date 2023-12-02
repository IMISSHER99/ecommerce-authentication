package org.acme.resource;

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


    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerUser(@RequestBody Users users) {
        Users registeredUser = userService.registerUser(users);
        return Response.status(200).entity(registeredUser).build();
    }
}
