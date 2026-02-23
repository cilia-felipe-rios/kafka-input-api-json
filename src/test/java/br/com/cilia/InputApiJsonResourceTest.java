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
            .post("/api/orders")
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
            .post("/api/orders")
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
            .body("""
                {
                  "customer": {"id": "C1", "name": "Test", "email": "test@test.com"},
                  "items": [{"sku": "SKU1", "description": "Item", "quantity": 1, "unitPrice": 10.00}]
                }
                """)
        .when()
            .post("/api/orders")
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
            .post("/api/orders")
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
            .post("/api/orders")
        .then()
            .statusCode(400)
            .body("success", is(false));
    }

    @Test
    void shouldReturn400WhenInvalidEnumValue() {
        given()
            .contentType("application/json")
            .header("auth_token", VALID_TOKEN)
            .body("""
                {"metadata": {"priority": "highest"}}
                """)
        .when()
            .post("/api/orders")
        .then()
            .statusCode(400)
            .body("success", is(false))
            .body("message", is("invalid value 'highest' for field metadata.priority"));
    }

    @Test
    void shouldReturn400WhenItemsEmpty() {
        given()
            .contentType("application/json")
            .header("auth_token", VALID_TOKEN)
            .body("""
                {"customer": {"name": "Test", "email": "test@test.com"}, "items": []}
                """)
        .when()
            .post("/api/orders")
        .then()
            .statusCode(400)
            .body("success", is(false));
    }

    @Test
    void shouldReturn400WhenCustomerNameMissing() {
        given()
            .contentType("application/json")
            .header("auth_token", VALID_TOKEN)
            .body("""
                {"customer": {"email": "test@test.com"}, "items": [{"sku": "S1", "description": "D", "quantity": 1, "unitPrice": 10}]}
                """)
        .when()
            .post("/api/orders")
        .then()
            .statusCode(400)
            .body("success", is(false));
    }

    @Test
    void shouldReturn400WhenEmailInvalid() {
        given()
            .contentType("application/json")
            .header("auth_token", VALID_TOKEN)
            .body("""
                {"customer": {"name": "Test", "email": "invalid"}, "items": [{"sku": "S1", "description": "D", "quantity": 1, "unitPrice": 10}]}
                """)
        .when()
            .post("/api/orders")
        .then()
            .statusCode(400)
            .body("success", is(false));
    }

    @Test
    void shouldReturn400WhenItemSkuMissing() {
        given()
            .contentType("application/json")
            .header("auth_token", VALID_TOKEN)
            .body("""
                {"customer": {"name": "Test", "email": "test@test.com"}, "items": [{"description": "D", "quantity": 1, "unitPrice": 10}]}
                """)
        .when()
            .post("/api/orders")
        .then()
            .statusCode(400)
            .body("success", is(false));
    }

    @Test
    void shouldReturn400WhenQuantityBelowMin() {
        given()
            .contentType("application/json")
            .header("auth_token", VALID_TOKEN)
            .body("""
                {"customer": {"name": "Test", "email": "test@test.com"}, "items": [{"sku": "S1", "description": "D", "quantity": 0, "unitPrice": 10}]}
                """)
        .when()
            .post("/api/orders")
        .then()
            .statusCode(400)
            .body("success", is(false));
    }

    @Test
    void shouldReturn400WhenQuantityAboveMax() {
        given()
            .contentType("application/json")
            .header("auth_token", VALID_TOKEN)
            .body("""
                {"customer": {"name": "Test", "email": "test@test.com"}, "items": [{"sku": "S1", "description": "D", "quantity": 100, "unitPrice": 10}]}
                """)
        .when()
            .post("/api/orders")
        .then()
            .statusCode(400)
            .body("success", is(false));
    }

    @Test
    void shouldReturn400WhenUnitPriceNegative() {
        given()
            .contentType("application/json")
            .header("auth_token", VALID_TOKEN)
            .body("""
                {"customer": {"name": "Test", "email": "test@test.com"}, "items": [{"sku": "S1", "description": "D", "quantity": 1, "unitPrice": -1}]}
                """)
        .when()
            .post("/api/orders")
        .then()
            .statusCode(400)
            .body("success", is(false));
    }
}
