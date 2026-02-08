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
class MenuItemControllerTest extends AbstractIT {

    @Nested
    class CreateMenuItemTests {

        @Test
        void shouldCreateMenuItemSuccessfully() {
            var payload = """
                    {
                      "menuId": 351,
                      "name": "Grilled Chicken Burger",
                      "description": "Grilled chicken breast with lettuce, tomato and garlic mayo in a brioche bun.",
                      "price": 10.50,
                      "category": "MAIN_COURSE",
                      "availability": true,
                      "imageUrl": "https://example.com/images/grilled-chicken-burger.jpg",
                      "dietaryInfo": "NONE"
                    }
                    
                    """;

            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/menu-items")
                    .then()
                    .statusCode(201)
                    .body("id",notNullValue())
                    .body("name",equalTo("Grilled Chicken Burger"))
                    .body("description",equalTo("Grilled chicken breast with lettuce, tomato and garlic mayo in a brioche bun."))
                    .body("price",equalTo(10.50f))
                    .body("category",equalTo("MAIN_COURSE"))
                    .body("availability",equalTo(true))
                    .body("imageUrl",equalTo("https://example.com/images/grilled-chicken-burger.jpg"))
                    .body("dietaryInfo",equalTo("NONE"));
        }


        @Test
        void shouldReturnBadRequestWhenPayloadIsInvalid() {
            var payload = """
                    {
                      "menuId": 351,
                      "name": "",
                      "description": "Grilled chicken breast with lettuce, tomato and garlic mayo in a brioche bun.",
                      "price": 10.50,
                      "category": "",
                      "availability": true,
                      "imageUrl": "https://example.com/images/grilled-chicken-burger.jpg",
                      "dietaryInfo": "NONE"
                    }
                    
                    """;

            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/menu-items")
                    .then()
                    .statusCode(400);
        }
    }

    @Nested
    class UpdateMenuItemTests {


        @Test
        void shouldUpdateMenuItemSuccessfully() {
            var payload = """
                    {
                      "name": "Updated Grilled Chicken Burger",
                      "description": "Updated description",
                      "price": 12.00,
                      "category": "MAIN_COURSE",
                      "availability": false,
                      "imageUrl": "https://example.com/images/updated-grilled-chicken-burger.jpg",
                      "dietaryInfo": "NONE"
                    }
                    
                    """;

            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .put("/api/menu-items/1")
                    .then()
                    .statusCode(200)
                    .body("id",equalTo(1))
                    .body("name",equalTo("Updated Grilled Chicken Burger"))
                    .body("description",equalTo("Updated description"))
                    .body("price",equalTo(12.00f))
                    .body("category",equalTo("MAIN_COURSE"))
                    .body("availability",equalTo(false))
                    .body("imageUrl",equalTo("https://example.com/images/updated-grilled-chicken-burger.jpg"))
                    .body("dietaryInfo",equalTo("NONE"));
        }

        @Test
        void shouldUpdateAvailabilitySuccessfully() {
            RestAssured.given()
                    .when()
                    .patch("/api/menu-items/1/availability?available=false")
                    .then()
                    .statusCode(204);

            // Verify that availability is updated
            RestAssured.given()
                    .when()
                    .get("/api/menu-items/1")
                    .then()
                    .statusCode(200)
                    .body("availability",equalTo(false));

        }

    }

    @Nested
    class DeleteMenuItemTests {

        @Test
        void shouldDeleteMenuItemSuccessfully() {
            RestAssured.given()
                    .when()
                    .delete("/api/menu-items/1")
                    .then()
                    .statusCode(204);

            // Verify that menu item is deleted
            RestAssured.given()
                    .when()
                    .get("/api/menu-items/1")
                    .then()
                    .statusCode(404);
        }


    }

    @Nested
    class GetMenuItemByIdTests {

        @Test
        void shouldGetMenuItemByIdSuccessfully() {
            RestAssured.given()
                    .when()
                    .get("/api/menu-items/1")
                    .then()
                    .statusCode(200)
                    .body("id",equalTo(1))
                    .body("name",equalTo("Butter Chicken"))
                    .body("description",equalTo("Creamy tomato gravy with tender chicken"))
                    .body("price",equalTo(12.50f))
                    .body("category",equalTo("MAIN_COURSE"))
                    .body("availability",equalTo(true))
                    .body("imageUrl",nullValue())
                    .body("dietaryInfo",equalTo("NONE"));


        }

        @Test
        void shouldReturnNotFoundWhenMenuItemDoesNotExist() {
            RestAssured.given()
                    .when()
                    .get("/api/menu-items/999")
                    .then()
                    .statusCode(404);
        }


        @Test
        void shouldReturnBadRequestWhenMenuItemIdIsInvalid() {
            RestAssured.given()
                    .when()
                    .get("/api/menu-items/abc")
                    .then()
                    .statusCode(400);
        }

        @Test
        void shouldGetMenuItemsByMenuIdSuccessfully() {
            RestAssured.given()
                    .when()
                    .get("/api/menu-items/menu/351")
                    .then()
                    .statusCode(200)
                    .body("$",hasSize(4));
        }

        @Test
        void shouldGetMenuItemsByCategorySuccessfully() {
            RestAssured.given()
                    .when()
                    .get("/api/menu-items/menu/351/category/DESSERT")
                    .then()
                    .statusCode(200)
                    .body("$",hasSize(4));
        }

        @Test
        void shouldReturnEmptyListWhenNoMenuItemsForMenuId() {
            RestAssured.given()
                    .when()
                    .get("/api/menu-items/menu/999")
                    .then()
                    .statusCode(200)
                    .body("$",hasSize(0));
        }

         @Test
        void shouldReturnEmptyListWhenNoMenuItemsForCategory() {
            RestAssured.given()
                    .when()
                    .get("/api/menu-items/menu/351/category/APPETIZER")
                    .then()
                    .statusCode(200)
                    .body("$",hasSize(0));
         }


         @Test
        void shouldReturnBadRequestWhenCategoryIsInvalid() {
            RestAssured.given()
                    .when()
                    .get("/api/menu-items/menu/351/category/INVALID_CATEGORY")
                    .then()
                    .statusCode(400);
         }

         @Test
        void shouldReturnNotFoundWhenMenuIdIsInvalid() {
            RestAssured.given()
                    .when()
                    .get("/api/menu-items/menu/abc")
                    .then()
                    .statusCode(400);
         }


    }


}