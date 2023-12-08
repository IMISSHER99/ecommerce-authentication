package org.acme.resource;

import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.acme.dto.ClientDto;
import org.acme.dto.UserDto;
import org.acme.entity.Client;
import org.acme.entity.Users;
import org.acme.service.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
@TestHTTPEndpoint(ClientResource.class)
class ClientResourceTest {

    @InjectMock
    ClientService clientService;

    @Test
    void testRegisterClient() {
        Mockito.when(clientService.registerClient(any(Client.class)))
                .thenReturn(new Client());
        given().contentType(ContentType.JSON).body(new Users())
                .when().post("register").then().statusCode(200);
        Mockito.verify(clientService, Mockito.atMostOnce()).registerClient(any(Client.class));
    }

    @Test
    void testAuthenticateUser() {
        Mockito.doNothing().when(clientService).authenticateClient(any(ClientDto.class));
        given().contentType(ContentType.JSON).body(new ClientDto())
                .when().post("login").then().statusCode(200);
        Mockito.verify(clientService, Mockito.atMostOnce()).authenticateClient(any(ClientDto.class));
    }



}