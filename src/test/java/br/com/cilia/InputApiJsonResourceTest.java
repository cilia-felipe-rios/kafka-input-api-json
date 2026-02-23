package br.com.cilia;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class InputApiJsonResourceTest {

    private static final String VALID_TOKEN = "VALID_TOKEN";

    @Test
    void shouldReturn401WhenAuthTokenMissing() {
        given()
            .contentType("application/json")
            .body("{\"test\": \"data\"}")
        .when()
            .post("/json-input-api")
        .then()
            .statusCode(401)
            .body("success", is(false))
            .body("message", is("authorization header not found"));
    }

    @Test
    void shouldReturn401WhenAuthTokenInvalid() {
        given()
            .contentType("application/json")
            .header("auth_token", "INVALID")
            .body("{\"test\": \"data\"}")
        .when()
            .post("/json-input-api")
        .then()
            .statusCode(401)
            .body("success", is(false))
            .body("message", is("invalid access token"));
    }

    @Test
    void shouldReturn200WhenValidRequest() {
        given()
            .contentType("application/json")
            .header("auth_token", VALID_TOKEN)
            .body("{\"test\": \"data\"}")
        .when()
            .post("/json-input-api")
        .then()
            .statusCode(200)
            .body("success", is(true))
            .body("message", is("Added to stream!"));
    }

    @Test
    void shouldReturn400WhenPayloadEmpty() {
        given()
            .contentType("application/json")
            .header("auth_token", VALID_TOKEN)
            .body("")
        .when()
            .post("/json-input-api")
        .then()
            .statusCode(400)
            .body("success", is(false));
    }

    @Test
    void shouldReturn400WhenPayloadInvalidJson() {
        given()
            .contentType("application/json")
            .header("auth_token", VALID_TOKEN)
            .body("not a json")
        .when()
            .post("/json-input-api")
        .then()
            .statusCode(400)
            .body("success", is(false));
    }
}
