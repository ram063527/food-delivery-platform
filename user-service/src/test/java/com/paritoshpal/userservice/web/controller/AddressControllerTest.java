package com.paritoshpal.userservice.web.controller;

import com.paritoshpal.userservice.AbstractIT;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@Sql("/test_data.sql")
class AddressControllerTest extends AbstractIT {


    @Nested
    class CreateAddressTests {

        @Test
        void shouldCreateAddressSuccessfully() {
            var payload = """
                    {
                      "userId": 1,
                      "streetAddress": "123 Elm St",
                      "city": "Springfield",
                      "state": "IL",
                      "postalCode": "62701",
                      "country": "USA"
                    }
                    
                    """;

            given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/address")
                    .then()
                    .statusCode(201)
                    .body("id",notNullValue())
                    .body("streetAddress",is("123 Elm St"))
                    .body("city",is("Springfield"))
                    .body("state",is("IL"))
                    .body("postalCode",is("62701"))
                    .body("country",is("USA"));

        }

        @Test
        void shouldReturnBadRequestWhenMandatoryFieldsAreMissing() {
            var payload = """
                    {
                      "userId": 1,
                      "streetAddress": "",
                      "city": "",
                      "state": "",
                      "postalCode": "",
                      "country": ""
                    }
                    """;

            given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/address")
                    .then()
                    .statusCode(400)
                    .body("errors",hasItems(
                            "Street address cannot be empty",
                            "City cannot be empty",
                            "State cannot be empty",
                            "Postal code cannot be empty",
                            "Country cannot be empty"
                    ));

        }



    }

    @Nested
    class UpdateAddressTests {

        @Test
        void shouldUpdateAddressSuccessfully() {
            Long userId = 1L;
            var payload = """
                    {
                      "streetAddress": "71 Sidney Grove",
                      "city": "Newcastle",
                      "state": "NCL",
                      "postalCode": "62702",
                      "country": "UK"
                    }
                    
                    """;

            given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .put("/api/address/{id}", userId)
                    .then()
                    .statusCode(200)
                    .body("id",is(1))
                    .body("streetAddress",is("71 Sidney Grove"))
                    .body("city",is("Newcastle"))
                    .body("state",is("NCL"))
                    .body("postalCode",is("62702"))
                    .body("country",is("UK"));

        }

        @Test
        void shouldMakeNoChangesWhenNoFieldsAreProvided() {
            Long userId = 1L;
            var payload = """
                    {
                    
                    }
                    
                    """;

            given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .put("/api/address/{id}", userId)
                    .then()
                    .statusCode(200)
                    .body("id", is(1))
                    .body("streetAddress", is("123 Elm Street"))
                    .body("city", is("New York"))
                    .body("state", is("NY"))
                    .body("postalCode", is("10001"))
                    .body("country", is("USA"));
        }

    }

    @Nested
    class GetAddressTests {

        @Test
        void shouldGetAddressByIdSuccessfully() {
            Long id = 1L;

            given()
                    .when()
                    .get("/api/address/id/{id}", id)
                    .then()
                    .statusCode(200)
                    .body("id", is(1))
                    .body("streetAddress", is("123 Elm Street"))
                    .body("streetAddress", is("123 Elm Street"))
                    .body("city", is("New York"))
                    .body("state", is("NY"))
                    .body("postalCode", is("10001"))
                    .body("country", is("USA"));
        }

        @Test
        void shouldReturnNotFoundWhenAddressDoesNotExist() {
            Long id = 999L;

            given()
                    .when()
                    .get("/api/address/id/{id}", id)
                    .then()
                    .statusCode(404);
        }

        @Test
        void shouldGetAddressByUserEmailSuccessfully() {
            String email = "alice.white@example.com";

            given()
                    .when()
                    .get("/api/address/email/{email}", email)
                    .then()
                    .statusCode(200)
                    .body("[0].id", notNullValue())
                    .body("[0].streetAddress", is("202 Birch Boulevard"))
                    .body("[0].city", is("Miami"))
                    .body("[0].state", is("FL"))
                    .body("[0].postalCode", is("33101"))
                    .body("[0].country", is("USA"));
        }

        @Test
        void shouldReturnEmailNotFoundWhenUserEmailDoesNotExist() {
            String email = "someEmail@gmail.com";

            given()
                    .when()
                    .get("/api/address/email/{email}", email)
                    .then()
                    .statusCode(404)
                    .body("detail",is(
                            "User with email "+email+" not found"
                    ));
        }

        @Test
        void shouldGetAddressByUserIdSuccessfully() {
            Long userId = 1L;

             given()
                    .when()
                    .get("/api/address/userId/{userId}", userId)
                    .then()
                    .statusCode(200)
                     .body("[0].id", notNullValue())
                     .body("[0].streetAddress", is("123 Elm Street"))
                     .body("[0].city", is("New York"))
                     .body("[0].state", is("NY"))
                     .body("[0].postalCode", is("10001"))
                     .body("[0].country", is("USA"));

        }

        @Test
        void shouldReturnNotFoundWhenUserDoesNotExists() {
            Long userId = 999L;

            given()
                    .when()
                    .get("/api/address/userId/{userId}", userId)
                    .then()
                    .statusCode(404)
                    .body("detail",is(
                            "User with id "+userId+" not found"
                    ));
        }




        @Test
        void shouldReturnEmptyListWhenUserHasNoAddress() {
            String email = "ram.white@example.com";
            given()
                    .when()
                    .get("/api/address/email/{email}", email)
                    .then()
                    .statusCode(200)
                    .body("$", hasSize(0));
        }


    }

    @Nested
    class  DeleteAddressTests {

        @Test
        void shouldDeleteAddressSuccessfully() {
            Long id = 1L;

            given()
                    .when()
                    .delete("/api/address/{id}", id)
                    .then()
                    .statusCode(204);

            // Verify that the address is deleted
            given()
                    .when()
                    .get("/api/address/id/{id}", id)
                    .then()
                    .statusCode(404);
        }
    }


}