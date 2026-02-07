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
class RestaurantControllerTest extends AbstractIT {


    @Nested
    class CreateRestaurantTests{

        @Test
        void shouldCreateRestaurantSuccessfully() {
            // Given
            var payload = """
                    {
                      "name": "Geordie Grill House",
                      "description": "Casual grill restaurant in Newcastle city centre specialising in steaks and burgers.",
                      "phone": "+44 191 555 5678",
                      "email": "hello@geordiegrillhouse.co.uk",
                      "ownerId": 2,
                      "cuisine": "Grill",
                      "status": "ACTIVE",
                 
                      "openingTime": "12:00:00",
                      "closingTime": "22:30:00",
                      "deliveryFee": 1.99,
                      "minimumOrderAmount": 12.00,
                      "estimatedDeliveryTime": 30
                    }
                    
                    """;

            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/restaurants")
                    .then()
                    .statusCode(201)
                    .body("id", notNullValue())
                    .body("name", is("Geordie Grill House"))
                    .body("description", is("Casual grill restaurant in Newcastle city centre specialising in steaks and burgers."))
                    .body("cuisine", is("Grill"))
                    .body("status", is("ACTIVE"))
                    .body("rating", is(0.0f));

        }

        @Test
        void shouldReturnConflictForDuplicateEmail() {
            // Given
            var payload = """
                    {
                     "name": "Geordie Grill House",
                      "description": "Casual grill restaurant in Newcastle city centre specialising in steaks and burgers.",
                      "phone": "+44 191 555 5678",
                      "email": "spicehub@mail.com",
                      "ownerId": 2,
                      "cuisine": "Grill",
                      "status": "ACTIVE",
                    
                      "openingTime": "12:00:00",
                      "closingTime": "22:30:00",
                      "deliveryFee": 1.99,
                      "minimumOrderAmount": 12.00,
                      "estimatedDeliveryTime": 30
                    }
                    
                    """;

            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/restaurants")
                    .then()
                    .statusCode(409);

        }


        @Test
        void shouldReturnBadRequestForInvalidPayload() {
            // Given
            var invalidPayload = """
                    {
                      "name": "", 
                      "description": "A restaurant with an empty name.",
                      "phone": "+44 191 555 5678",
                      "email": "invalid-email-format",
                      "ownerId": 2,
                      "cuisine": "Grill",
                      "status": "ACTIVE",
                      "openingTime": "12:00:00",
                      "closingTime": "22:30:00",
                      "deliveryFee": -1.99,
                      "minimumOrderAmount": -10.00,
                      "estimatedDeliveryTime": -30
                    }
                    """;

            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(invalidPayload)
                    .when()
                    .post("/api/restaurants")
                    .then()
                    .statusCode(400)
                    .body("errors", containsInAnyOrder(
                            "Restaurant name cannot be empty",
                            "must be a well-formed email address",
                            "Delivery fee cannot be negative",
                            "Minimum order amount cannot be negative",
                            "Estimated delivery time cannot be negative"
                    ));
        }

    }

    @Nested
    class UpdateRestaurantTests{

        @Test
        void shouldUpdateRestaurantSuccessfully(){

            var payload = """
                    {
                      "name": "Updated Restaurant Name",
                      "description": "Updated description for the restaurant.",
                      "phone": "+44 191 555 5678",
                      "cuisine": "Italian",
                      "status": "INACTIVE",
                      "openingTime": "11:00:00",
                      "closingTime": "23:00:00",
                      "deliveryFee": 2.99,
                      "minimumOrderAmount": 15.00,
                      "estimatedDeliveryTime": 25
                    }
                    """;

                RestAssured.given()
                        .contentType(ContentType.JSON)
                        .body(payload)
                        .when()
                        .put("/api/restaurants/1")
                        .then()
                        .statusCode(200)
                        .body("id", notNullValue())
                        .body("name", is("Updated Restaurant Name"))
                        .body("description", is("Updated description for the restaurant."))
                        .body("cuisine", is("Italian"))
                        .body("status", is("INACTIVE"));

        }

        @Test
        void shouldUpdateRestaurantStatusSuccessfully() {
            RestAssured.given()
                    .when()
                    .patch("/api/restaurants/1/status?status=ACTIVE")
                    .then()
                    .statusCode(200)
                    .body("id", notNullValue())
                    .body("status", is("ACTIVE"));

        }

    }

    @Nested
    class DeleteRestaurantTests{

        @Test
        void shouldDeleteRestaurantSuccessfully(){
            RestAssured.given()
                    .when()
                    .delete("/api/restaurants/1")
                    .then()
                    .statusCode(204);

            // Verify that the restaurant is actually deleted
            RestAssured.given()
                    .when()
                    .get("/api/restaurants/id/1")
                    .then()
                    .statusCode(404);
        }

    }

    @Nested
    class GetRestaurantTests {

        @Test
        void shouldGetRestaurantByIdSuccessfully() {
            RestAssured.given()
                    .when()
                    .get("/api/restaurants/id/1")
                    .then()
                    .statusCode(200)
                    .body("id", is(1))
                    .body("name",is("Spice Hub"))
                    .body("cuisine", is("INDIAN"));
        }

        @Test
        void shouldReturnNotFoundForNonExistingRestaurant() {
            RestAssured.given()
                    .when()
                    .get("/api/restaurants/id/999")
                    .then()
                    .statusCode(404);
        }


        @Test
        void shouldGetAllRestaurantsWithPagination() {
            RestAssured.given()
                    .when()
                    .get("/api/restaurants?pageNo=1")
                    .then()
                    .statusCode(200)
                    .body("content", not(empty()))
                    .body("pageNumber", is(1))
                    .body("totalElements", is(5))
                    .body("totalPages", is(1));
        }

        @Test
        void shouldGetAllRestaurantsByOwnerId() {
            RestAssured.given()
                    .when()
                    .get("/api/restaurants/owner/2")
                    .then()
                    .statusCode(200)
                    .body("$", hasSize(1))
                    .body("[0].name", is("Green Bowl"))
                    .body("[0].cuisine", is("VEGAN")); // matches your seed data
        }

        @Test
        void shouldGetAllRestaurantsByName() {
            RestAssured.given()
                    .when()
                    .get("/api/restaurants/name/Spice Hub")
                    .then()
                    .statusCode(200)
                    .body("$", not(empty()))
                    .body("name", everyItem(is("Spice Hub")));
        }

        @Test
        void shouldGetAllRestaurantsByCuisine() {
            RestAssured.given()
                    .when()
                    .get("/api/restaurants/cuisine/ITALIAN")
                    .then()
                    .statusCode(200)
                    .body("$", not(empty()))
                    .body("cuisine", everyItem(is("ITALIAN")));
        }

        @Test
        void shouldGetAllRestaurantsByCiyt() {
            RestAssured.given()
                    .when()
                    .get("/api/restaurants/city/Bangalore")
                    .then()
                    .statusCode(200)
                    .body("$", not(empty()))
                    .body("address.city", everyItem(is("Bangalore")));
        }

        @Test
        void ShouldSearchRestaurantsByMultipleCriteria() {
            RestAssured.given()
                    .queryParam("name", "Spice Hub")
                    .queryParam("cuisine", "INDIAN")
                    .queryParam("city", "Bangalore")
                    .when()
                    .get("/api/restaurants/search")
                    .then()
                    .statusCode(200)
                    .body("$", not(empty()))
                    .body("data", not(empty()))
                    .body("data.name", everyItem(is("Spice Hub")))
                    .body("data.cuisine", everyItem(is("INDIAN")))
                    .body("data.address.city", everyItem(is("Bangalore")));
        }


        @Test
        void ShouldReturnEmptyResultForSearchWithNoMatches() {
            RestAssured.given()
                    .queryParam("name", "NonExistingRestaurant")
                    .when()
                    .get("/api/restaurants/search")
                    .then()
                    .statusCode(200)
                    .body("$", not(empty()))
                    .body("data", empty());
        }

        @Test
        void shouldReturnEverythingForSearchWithNoFilters() {
            RestAssured.given()
                    .when()
                    .get("/api/restaurants/search")
                    .then()
                    .statusCode(200)
                    .body("$", not(empty()))
                    .body("data", hasSize(5));
        }

    }


}