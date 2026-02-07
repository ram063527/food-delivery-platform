package com.paritoshpal.restaurantservice.web.controller;

import com.paritoshpal.restaurantservice.AbstractIT;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@Sql("/test-data.sql")
class RestaurantAddressControllerTest extends AbstractIT {


    @Nested
    class CreateRestaurantAddressTests {

        @Test
        void shouldCreateRestaurantAddressSuccessfully() {

            var payload = """
                    {
                      "restaurantId": 251,
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
                    .post("/api/restaurants-addresses")
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
                      "restaurantId": 1,
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
                    .post("/api/restaurants-addresses")
                    .then()
                    .statusCode(409)
                    .body("detail", is("Restaurant with id "+restaurantId  +" already has an address."));
        }


        @Test
        void shouldReturnBadRequestWhenPayloadIsInvalid(){

            var payload = """
                    {
                      "restaurantId": null,
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
                    .post("/api/restaurants-addresses")
                    .then()
                    .statusCode(400)
                    .body("errors", containsInAnyOrder(
                            "Restaurant ID cannot be null",
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
        void shouldUpdateRestaurantAddressSuccessfully() {
            Long addressId = 1L;
            var payload = """
                    {
                      "streetAddress": "Updated Street",
                      "city": "Updated City",
                      "state": "Updated State",
                      "postalCode": "Updated Postal Code",
                      "country": "Updated Country",
                      "latitude": 40.7128,
                      "longitude": -74.0060
                    }
                    
                    """;
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .put("/api/restaurants-addresses/{addressId}", addressId)
                    .then()
                    .statusCode(200)
                    .body("id", is(addressId.intValue()))
                    .body("streetAddress", is("Updated Street"))
                    .body("city", is("Updated City"))
                    .body("state", is("Updated State"))
                    .body("postalCode", is("Updated Postal Code"))
                    .body("country", is("Updated Country"))
                    .body("latitude", is(40.7128f))
                    .body("longitude", is(-74.0060f));
        }

        @Test
        void shouldReturnNotFoundWhenAddressDoesNotExist() {
            Long addressId = 999L;
            var payload = """
                    {
                      "streetAddress": "Updated Street",
                      "city": "Updated City",
                      "state": "Updated State",
                      "postalCode": "Updated Postal Code",
                      "country": "Updated Country",
                      "latitude": 40.7128,
                      "longitude": -74.0060
                    }
                    
                    """;
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .put("/api/restaurants-addresses/{addressId}", addressId)
                    .then()
                    .statusCode(404)
                    .body("detail", is("Restaurant address not found for address ID: "+addressId));
        }
    }

    @Nested
    class DeleteRestaurantAddressTests {

        @Test
        void shouldDeleteRestaurantAddressByIdSuccessfully() {
            Long addressId = 1L;
            RestAssured.given()
                    .when()
                    .delete("/api/restaurants-addresses/{addressId}", addressId)
                    .then()
                    .statusCode(204);
            // Verify that the address is actually deleted
            RestAssured.given()
                    .when()
                    .get("/api/restaurants-addresses/{addressId}", addressId)
                    .then()
                    .statusCode(404);
        }

        @Test
        void shouldDeleteRestaurantAddressByRestaurantIdSuccessfully() {
            Long restaurantId = 51L;
            RestAssured.given()
                    .when()
                    .delete("/api/restaurants-addresses/restaurant/{restaurantId}", restaurantId)
                    .then()
                    .statusCode(204);
            // Verify that the address is actually deleted
            RestAssured.given()
                    .when()
                    .get("/api/restaurants-addresses/restaurant/{restaurantId}", restaurantId)
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
                    .get("/api/restaurants-addresses/restaurant/{restaurantId}", restaurantId)
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
                    .get("/api/restaurants-addresses/restaurant/{restaurantId}", nonExistingRestaurantId)
                    .then()
                    .statusCode(404);
        }

        @Test
        void shouldGetAddressByAddressIdSuccessfully() {
            // Assuming Address ID 1 corresponds to your first restaurant
            Long addressId = 1L;

            RestAssured.given()
                    .when()
                    .get("/api/restaurants-addresses/{addressId}", addressId)
                    .then()
                    .statusCode(200)
                    .body("id", is(1))
                    .body("city", notNullValue())
                    .body("postalCode", notNullValue());
        }

        @Test
        void shouldReturnNotFoundForNonExistingAddressId() {
            Long nonExistingAddressId = 999L;

            RestAssured.given()
                    .when()
                    .get("/api/restaurants-addresses/{addressId}", nonExistingAddressId)
                    .then()
                    .statusCode(404);
        }
    }

}