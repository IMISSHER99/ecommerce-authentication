package org.acme.resource;

import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.acme.constants.UserRole;
import org.acme.dto.ClientDto;
import org.acme.dto.UserDto;
import org.acme.entity.Role;
import org.acme.entity.Users;
import org.acme.exceptions.AccessDeniedException;
import org.acme.service.ClientService;
import org.acme.service.UserService;
import org.acme.utils.AuthenticationUtils;
import org.jose4j.jwk.Use;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;

@QuarkusTest
@TestHTTPEndpoint(AuthResource.class)
class AuthResourceTest {

    @InjectMock
    ClientService clientService;
    @InjectMock
    UserService userService;
    @InjectMock
    AuthenticationUtils authenticationUtils;

    private static final String MOCK = "Basic TU9DSzEyMzpNT0NL";

    @Test
    void testAuthenticate() throws Exception {
        Mockito.when(authenticationUtils.extractCredentials(anyString())).thenReturn(new String[]{MOCK, MOCK});
        Mockito.doNothing().when(clientService).authenticateClient(any(ClientDto.class));
        Mockito.when(userService.authenticateUser(any(UserDto.class))).thenReturn(new Users());
        Mockito.when(authenticationUtils.generateAuthorizationCode(any(), anyString())).thenReturn(MOCK);

        given().contentType(ContentType.JSON).body(new UserDto())
                .header("Authorization", MOCK)
                .when().post("request").then().statusCode(200);
    }

    @Test
    void testAuthenticate_accessDenied() throws Exception {
        Mockito.when(authenticationUtils.extractCredentials(anyString())).thenThrow(AccessDeniedException.class);
        Mockito.doNothing().when(clientService).authenticateClient(any(ClientDto.class));
        Mockito.when(userService.authenticateUser(any(UserDto.class))).thenReturn(new Users());
        Mockito.when(authenticationUtils.generateAuthorizationCode(any(), anyString())).thenReturn(MOCK);

        given().contentType(ContentType.JSON).body(new UserDto())
                .header("Authorization", MOCK)
                .when().post("request").then().statusCode(501);
    }

}