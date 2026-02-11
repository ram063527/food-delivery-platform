package com.paritoshpal.restaurantservice.web.controller;

import com.paritoshpal.restaurantservice.AbstractIT;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.*;


@Sql("/test-data.sql")
class RestaurantAddressControllerTest extends AbstractIT {


    @Nested
    class CreateRestaurantAddressTests {

        @Test
        void shouldCreateRestaurantAddressSuccessfully() {

            Long restaurantId = 251L;
            var payload = """
                    {
             
                      "streetAddress": "12 Grey Street",
                      "city": "Newcastle upon Tyne",
                      "state": "Tyne and Wear",
                      "postalCode": "NE1 6AE",
                      "country": "United Kingdom",
                      "latitude": 54.9723,
                      "longitude": -1.6139
                    }
                    
                    """;
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/restaurants/{restaurantId}/address", restaurantId)
                    .then()
                    .statusCode(201)
                    .body("id", notNullValue())
                    .body("streetAddress", is("12 Grey Street"))
                    .body("city", is("Newcastle upon Tyne"))
                    .body("state", is("Tyne and Wear"))
                    .body("postalCode", is("NE1 6AE"))
                    .body("country", is("United Kingdom"))
                    .body("latitude", is(54.9723f))
                    .body("longitude", is(-1.6139f));
        }

        @Test
        void shouldReturnConflictWhenAddressAlreadyExistsForRestaurant() {
            Long restaurantId = 1L;
            var payload = """
                    {
                   
                      "streetAddress": "12 Grey Street",
                      "city": "Newcastle upon Tyne",
                      "state": "Tyne and Wear",
                      "postalCode": "NE1 6AE",
                      "country": "United Kingdom",
                      "latitude": 54.9723,
                      "longitude": -1.6139
                    }
                    
                    """;
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/restaurants/{restaurantId}/address", restaurantId)
                    .then()
                    .statusCode(409)
                    .body("detail", is("Restaurant with id "+restaurantId  +" already has an address."));
        }


        @Test
        void shouldReturnBadRequestWhenPayloadIsInvalid(){

            Long restaurantId = 51L;
            var payload = """
                    {
                      "streetAddress": "",
                      "city": "",
                      "state": "",
                      "postalCode": "",
                      "country": "",
                      "latitude": 54.9723,
                      "longitude": -1.6139
                    }
                    
                    """;
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/restaurants/{restaurantId}/address", restaurantId)
                    .then()
                    .statusCode(400)
                    .body("errors", containsInAnyOrder(

                            "Street address cannot be empty",
                            "City cannot be empty",
                            "State cannot be empty",
                            "Postal code cannot be empty",
                            "Country cannot be empty"
                    ));
        }

    }

    @Nested
    class UpdateRestaurantAddressTests {

        @Test
        void shouldUpdateRestaurantAddressSuccessfully(){
            Long restaurantId = 1L;

            var payload = """
                    {
                      "streetAddress": "13 Grey Street",
                      "city": "Newcastle upon Tyne",
                      "state": "Tyne and Wear",
                      "postalCode": "NE1 6AE",
                      "country": "United Kingdom",
                      "latitude": 54.9723,
                      "longitude": -1.6139
                    }
                    """;

            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .put("/api/restaurants/{restaurantId}/address", restaurantId)
                    .then()
                    .statusCode(200)
                    .body("id", notNullValue())
                    .body("streetAddress", is("13 Grey Street"))
                    .body("city", is("Newcastle upon Tyne"))
                    .body("state", is("Tyne and Wear"))
                    .body("postalCode", is("NE1 6AE"))
                    .body("country", is("United Kingdom"))
                    .body("latitude", is(54.9723f))
                    .body("longitude", is(-1.6139f));
        }

        @Test
        void shouldReturnNotFoundWhenRestaurantDoesNotExists(){
            Long nonExistantRestaurantId = 9999L;

            var payload = """
                    {
                      "streetAddress": "13 Grey Street",
                      "city": "Newcastle upon Tyne",
                      "state": "Tyne and Wear",
                      "postalCode": "NE1 6AE",
                      "country": "United Kingdom",
                      "latitude": 54.9723,
                      "longitude": -1.6139
                    }
                    """;

            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .put("/api/restaurants/{restaurantId}/address", nonExistantRestaurantId)
                    .then()
                    .statusCode(404);
        }
    }

    @Nested
    class DeleteRestaurantAddressTests {



        @Test
        void shouldDeleteRestaurantAddressByRestaurantIdSuccessfully() {
            Long restaurantId = 51L;
            RestAssured.given()
                    .when()
                    .delete("/api/restaurants/{restaurantId}/address", restaurantId)
                    .then()
                    .statusCode(204);
            // Verify that the address is actually deleted
            RestAssured.given()
                    .when()
                    .get("/api/restaurants/{restaurantId}/address", restaurantId)
                    .then()
                    .statusCode(404);
        }

    }

    @Nested
    class GetRestaurantAddressTests{
        @Test
        void shouldGetAddressByRestaurantIdSuccessfully() {
            // ID 1 is Spice Hub in your seed data
            Long restaurantId = 1L;

            RestAssured.given()
                    .when()
                    .get("/api/restaurants/{restaurantId}/address", restaurantId)
                    .then()
                    .statusCode(200)
                    .body("id", notNullValue())
                    .body("city", is("Bangalore"));
        }

        @Test
        void shouldReturnNotFoundForNonExistingRestaurantId() {
            Long nonExistingRestaurantId = 999L;

            RestAssured.given()
                    .when()
                    .get("/api/restaurants/{restaurantId}/address", nonExistingRestaurantId)
                    .then()
                    .statusCode(404);
        }

    }

}