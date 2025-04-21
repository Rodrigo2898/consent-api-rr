package com.sensedia.sample.consents.integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.utility.TestcontainersConfiguration;

@Import({TestcontainersConfiguration.class})
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
public class ConsentResourceIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void shouldCreateConsentSuccessfully() {
        // Arrange
        String requestBody = """
        {
          "cpf": "899.732.810-71",
          "status": "ACTIVE",
          "expirationDateTime": "2025-12-31T23:59:59",
          "additionalInfo": "Consentimento para uso de dados pessoais"
        }
        """;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/consents")
                .then()
                .statusCode(201)
                .body("cpf", Matchers.equalTo("899.732.810-71"))
                .body("status", Matchers.equalTo("ACTIVE"))
                .body("expirationDateTime", Matchers.equalTo("2025-12-31T23:59:59"))
                .body("additionalInfo", Matchers.equalTo("Consentimento para uso de dados pessoais"));
    }
}
