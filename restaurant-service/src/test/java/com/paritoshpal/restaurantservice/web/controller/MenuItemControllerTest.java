package com.paritoshpal.restaurantservice.web.controller;

import com.paritoshpal.restaurantservice.AbstractIT;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.*;

@Sql("/test-data.sql")
class MenuItemControllerTest extends AbstractIT {

    @Nested
    class CreateMenuItemTests {

        @Test
        void shouldCreateMenuItemSuccessfully() {
            Long restaurantId = 201L; // Sweet Treats
            Long menuId = 351L; // Dessert Menu

            var payload = """
                    {
                 
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
                    .post("/api/restaurants/{restaurantId}/menus/{menuId}/items", restaurantId, menuId)
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

            Long restaurantId = 201L;
            Long menuId = 351L;

            var payload = """
                    {
                   
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
                    .post("/api/restaurants/{restaurantId}/menus/{menuId}/items", restaurantId, menuId)
                    .then()
                    .statusCode(400);
        }
        @Test
        void shouldReturnNotFoundWhenMenuDoesNotExist() {
            Long restaurantId = 1L;
            Long menuId = 999L; // Non-existent menu

            var payload = """
                    {
                      "name": "Grilled Chicken Burger",
                      "description": "Delicious burger",
                      "price": 10.50,
                      "category": "MAIN_COURSE",
                      "availability": true,
                      "imageUrl": "https://example.com/images/burger.jpg",
                      "dietaryInfo": "NONE"
                    }
                    """;

            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/restaurants/{restaurantId}/menus/{menuId}/items", restaurantId, menuId)
                    .then()
                    .statusCode(404)
                    .body("detail", containsString("Menu with id " + menuId + " not found"));
        }

        @Test
        void shouldReturnNotFoundWhenRestaurantDoesNotExist() {
            Long restaurantId = 999L; // Non-existent restaurant
            Long menuId = 1L;

            var payload = """
                    {
                      "name": "Grilled Chicken Burger",
                      "description": "Delicious burger",
                      "price": 10.50,
                      "category": "MAIN_COURSE",
                      "availability": true,
                      "imageUrl": "https://example.com/images/burger.jpg",
                      "dietaryInfo": "NONE"
                    }
                    """;

            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/restaurants/{restaurantId}/menus/{menuId}/items", restaurantId, menuId)
                    .then()
                    .statusCode(404)
                    .body("detail", containsString("restaurant"));
        }

    }

    @Nested
    class UpdateMenuItemTests {


        @Test
        void shouldUpdateMenuItemSuccessfully() {

            Long restaurantId = 1L; // Spice Hub
            Long menuId = 1L; // Main Menu
            Long itemId = 1L; // Butter Chicken

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
                    .put("/api/restaurants/{restaurantId}/menus/{menuId}/items/{id}", restaurantId, menuId, itemId)
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
            Long restaurantId = 1L; // Spice Hub
            Long menuId = 1L; // Main Menu
            Long itemId = 1L; // Butter Chicken

            RestAssured.given()
                    .when()
                    .patch("/api/restaurants/{restaurantId}/menus/{menuId}/items/{id}/availability?available=false",
                            restaurantId, menuId, itemId)
                    .then()
                    .statusCode(204);

            // Verify that availability is updated
            RestAssured.given()
                    .when()
                    .get("/api/restaurants/{restaurantId}/menus/{menuId}/items/{id}", restaurantId, menuId, itemId)
                    .then()
                    .statusCode(200)
                    .body("availability",equalTo(false));

        }
        @Test
        void shouldReturnNotFoundWhenUpdatingNonExistentMenuItem() {
            Long restaurantId = 1L;
            Long menuId = 1L;
            Long itemId = 999L; // Non-existent item

            var payload = """
                    {
                      "name": "Updated Item",
                      "description": "Updated description",
                      "price": 15.00,
                      "category": "MAIN_COURSE",
                      "availability": true,
                      "imageUrl": "https://example.com/images/item.jpg",
                      "dietaryInfo": "NONE"
                    }
                    """;

            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .put("/api/restaurants/{restaurantId}/menus/{menuId}/items/{id}", restaurantId, menuId, itemId)
                    .then()
                    .statusCode(404)
                    .body("detail", containsString("Menu item with id " + itemId + " not found"));
        }

    }

    @Nested
    class DeleteMenuItemTests {

        @Test
        void shouldDeleteMenuItemSuccessfully() {
            Long restaurantId = 1L; // Spice Hub
            Long menuId = 1L; // Main Menu
            Long itemId = 51L; //Paneer tikkka

            RestAssured.given()
                    .when()
                    .delete("/api/restaurants/{restaurantId}/menus/{menuId}/items/{id}", restaurantId, menuId, itemId)
                    .then()
                    .statusCode(204);

            // Verify that menu item is deleted
            RestAssured.given()
                    .when()
                    .get("/api/restaurants/{restaurantId}/menus/{menuId}/items/{id}", restaurantId, menuId, itemId)
                    .then()
                    .statusCode(404);
        }

        @Test
        void shouldReturnNotFoundWhenDeletingNonExistentMenuItem() {
            Long restaurantId = 1L;
            Long menuId = 1L;
            Long itemId = 999L; // Non-existent item

            RestAssured.given()
                    .when()
                    .delete("/api/restaurants/{restaurantId}/menus/{menuId}/items/{id}", restaurantId, menuId, itemId)
                    .then()
                    .statusCode(404)
                    .body("detail", containsString("Menu item with id " + itemId + " not found"));
        }



    }

    @Nested
    class GetMenuItemByIdTests {

        @Test
        void shouldGetMenuItemByIdSuccessfully() {

            Long restaurantId = 1L; // Spice Hub
            Long menuId = 1L; // Main Menu
            Long itemId = 1L; // Butter Chicken

            RestAssured.given()
                    .when()
                    .get("/api/restaurants/{restaurantId}/menus/{menuId}/items/{id}", restaurantId, menuId, itemId)
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

            Long restaurantId = 1L;
            Long menuId = 1L;
            Long itemId = 999L; // Non-existent item

            RestAssured.given()
                    .when()
                    .get("/api/restaurants/{restaurantId}/menus/{menuId}/items/{id}", restaurantId, menuId, itemId)
                    .then()
                    .statusCode(404)
                    .body("detail", containsString("Menu item with id " + itemId + " not found"));
        }


        @Test
        void shouldReturnBadRequestWhenMenuItemIdIsInvalid() {
            Long restaurantId = 1L;
            Long menuId = 1L;

            RestAssured.given()
                    .when()
                    .get("/api/restaurants/{restaurantId}/menus/{menuId}/items/{id}", restaurantId, menuId, "abc")
                    .then()
                    .statusCode(400);
        }

        @Test
        void shouldGetMenuItemsByMenuIdSuccessfully() {
            Long restaurantId = 201L; // Sweet Treats
            Long menuId = 351L; // Dessert Menu with 4 items

            RestAssured.given()
                    .when()
                    .get("/api/restaurants/{restaurantId}/menus/{menuId}/items", restaurantId, menuId)
                    .then()
                    .statusCode(200)
                    .body("$",hasSize(4));
        }

        @Test
        void shouldGetMenuItemsByCategorySuccessfully() {
            Long restaurantId = 201L; // Sweet Treats
            Long menuId = 351L; // Dessert Menu

            RestAssured.given()
                    .when()
                    .get("/api/restaurants/{restaurantId}/menus/{menuId}/items", restaurantId, menuId)
                    .then()
                    .statusCode(200)
                    .body("$",hasSize(4));
        }

        @Test
        void shouldReturnEmptyListWhenNoMenuItemsForMenuId() {
            Long restaurantId = 201L; // Sweet Treats
            Long menuId = 351L; // Dessert Menu
            RestAssured.given()
                    .queryParam("category", "APPETIZER")
                    .when()
                    .get("/api/restaurants/{restaurantId}/menus/{menuId}/items", restaurantId, menuId)
                    .then()
                    .statusCode(200)
                    .body("$", hasSize(0));
        }


        @Test
        void shouldReturnNotFoundWhenMenuDoesNotExist() {
            Long restaurantId = 1L;
            Long menuId = 999L; // Non-existent menu

            RestAssured.given()
                    .when()
                    .get("/api/restaurants/{restaurantId}/menus/{menuId}/items", restaurantId, menuId)
                    .then()
                    .statusCode(404)
                    .body("detail", containsString("Menu with id " + menuId + " not found"));
        }

        @Test
        void shouldGetMainCourseItemsSuccessfully() {
            Long restaurantId = 1L; // Spice Hub
            Long menuId = 1L; // Main Menu

            RestAssured.given()
                    .queryParam("category", "MAIN_COURSE")
                    .when()
                    .get("/api/restaurants/{restaurantId}/menus/{menuId}/items", restaurantId, menuId)
                    .then()
                    .statusCode(200)
                    .body("$", hasSize(greaterThan(0)))
                    .body("[0].category", equalTo("MAIN_COURSE"));
        }


        @Test
        void shouldGetMenuItemsInBulkSuccessfully() {
            Long restaurantId = 1L; // Spice Hub
            Long menuId = 1L; // Main Menu

            var payload = """
                    [1, 51, 101]
                    """;

            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/restaurants/{restaurantId}/menus/{menuId}/items/bulk", restaurantId, menuId)
                    .then()
                    .statusCode(200)
                    .body("$", hasSize(3))
                    .body("[0].id", equalTo(1))
                    .body("[1].id", equalTo(51))
                    .body("[2].id", equalTo(101));
        }



    }


}