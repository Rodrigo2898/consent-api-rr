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


    @Test
    void shouldReturnAllConsentsSuccessfully() {
        createTestConsent("111.111.111-11", "ACTIVE");
        createTestConsent("222.222.222-22", "INACTIVE");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/consents")
                .then()
                .statusCode(200)
                .body("$", Matchers.hasSize(Matchers.greaterThanOrEqualTo(2)))
                .body("[0].id", Matchers.notNullValue())
                .body("[0].cpf", Matchers.notNullValue())
                .body("[0].status", Matchers.isOneOf("ACTIVE", "INACTIVE", "REVOKED"))
                .body("[0].creationDateTime", Matchers.notNullValue())
                .body("[0].expirationDateTime", Matchers.notNullValue())
                .body("[0].additionalInfo", Matchers.any(String.class));
    }

    @Test
    void shouldFindConsentById() {
        String requestBody = """
        {
          "cpf": "899.732.810-71",
          "status": "ACTIVE",
          "expirationDateTime": "2025-12-31T23:59:59",
          "additionalInfo": "Consentimento para uso de dados pessoais"
        }
        """;

        var id = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/consents")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/consents/" + id)
                .then()
                .statusCode(200)
                .body("cpf", Matchers.equalTo("899.732.810-71"))
                .body("status", Matchers.equalTo("ACTIVE"))
                .body("expirationDateTime", Matchers.equalTo("2025-12-31T23:59:59"));
    }


    @Test
    void shouldUpdateConsentSuccessfully() {
        String requestBody = """
            {
              "cpf": "899.732.810-71",
              "status": "ACTIVE",
              "expirationDateTime": "2025-12-31T23:59:59",
              "additionalInfo": "Consentimento para uso de dados pessoais"
            }
            """;

        var id = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/consents")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        String updateBody = """
            {
              "status": "REVOKED",
              "expirationDateTime": "2024-12-31T23:59:59",
              "additionalInfo": "Consentimento revogado"
            }
            """;

        // Act & Assert
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(updateBody)
                .when()
                .put("/consents/" + id)
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(id))
                .body("status", Matchers.equalTo("REVOKED"))
                .body("expirationDateTime", Matchers.equalTo("2024-12-31T23:59:59"))
                .body("additionalInfo", Matchers.equalTo("Consentimento revogado"));
    }


    private void createTestConsent(String cpf, String status) {
        String json = String.format("""
                            {
                              "cpf": "%s",
                              "status": "%s",
                              "expirationDateTime": "2025-12-31T23:59:59"
                            }
                        """, cpf, status);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(json)
                .post("/consents");
    }
}
