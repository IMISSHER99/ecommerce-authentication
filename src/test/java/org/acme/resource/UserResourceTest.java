package org.acme.resource;

import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.acme.dto.UserDto;
import org.acme.entity.Users;
import org.acme.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@QuarkusTest
@TestHTTPEndpoint(UserResource.class)
class UserResourceTest {

    private static final String MOCK_USER = "MOCK_USER";
    private static final String MOCK_ROLE = "ADMIN";
    @InjectMock
    UserService userService;


    @Test
    void testRegisterUser() {
        Mockito.when(userService.registerUser(any(Users.class))).thenReturn(new Users());
        given().contentType(ContentType.JSON).body(new Users())
                .when().post("register").then().statusCode(200);
        Mockito.verify(userService, Mockito.atMostOnce()).registerUser(any(Users.class));
    }

    @Test
    @TestSecurity(user = MOCK_USER, roles = {MOCK_ROLE})
    void testAuthenticateUser() {
        Mockito.when(userService.authenticateUser(any(UserDto.class))).thenReturn(new Users());
        given().contentType(ContentType.JSON).body(new UserDto())
                .when().post("login").then().statusCode(200);
        Mockito.verify(userService, Mockito.atMostOnce()).registerUser(any(Users.class));
    }
}