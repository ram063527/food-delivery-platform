package com.paritoshpal.restaurantservice.web.controller;


import com.paritoshpal.restaurantservice.AbstractIT;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;

@Sql("/test-data.sql")
class MenuControllerTest extends AbstractIT {


    @Nested
    class CreateMenuTests {


        @Test
        void shouldCreateMenuSuccessfully() {
            var payload  = """
                    {
                      "restaurantId": 251,
                      "name": "BRUNCH",
                      "description": "BRuch menu with beautiful chiken"
                    
                    }
                   
                    """;

            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/menus")
                    .then()
                    .statusCode(201)
                    .body("id",notNullValue())
                    .body("name",is("BRUNCH"))
                    .body("description",is("BRuch menu with beautiful chiken"));

        }

        @Test
        void shouldCreateMenuWithMenuItemsSuccessfully() {
            var payload = """
                    {
                      "restaurantId": 251,
                      "name": "Main Menu",
                      "description": "All‑day menu with popular dishes.",
                      "items": [
                        {
                          "menuId": 1,
                          "name": "Margherita Pizza",
                          "description": "Stone‑baked pizza with tomato, mozzarella and basil.",
                          "price": 9.50,
                          "category": "MAIN_COURSE",
                          "availability": true,
                          "imageUrl": "https://example.com/images/margherita-pizza.jpg",
                          "dietaryInfo": "VEGETARIAN"
                        },
                        {
                          "menuId": 1,
                          "name": "Tomato Soup",
                          "description": "Creamy tomato soup served with crusty bread.",
                          "price": 4.75,
                          "category": "BREAKFAST",
                          "availability": true,
                          "imageUrl": "https://example.com/images/tomato-soup.jpg",
                          "dietaryInfo": "VEGAN"
                        }
                      ]
                    }
                    
                    """;
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/menus")
                    .then()
                    .statusCode(201)
                    .body("id",notNullValue())
                    .body("name",is("Main Menu"))
                    .body("description",is("All‑day menu with popular dishes."))
                    .body("menuItems.size()",is(2))
                    // Sort the menuItems by name or id to ensure consistency in order
                    .body("menuItems.sort { it.name }.name", contains("Margherita Pizza", "Tomato Soup"))

                    // Validate the "Margherita Pizza" item properties
                    .body("menuItems.find { it.name == 'Margherita Pizza' }.id", notNullValue())
                    .body("menuItems.find { it.name == 'Margherita Pizza' }.description", is("Stone‑baked pizza with tomato, mozzarella and basil."))
                    .body("menuItems.find { it.name == 'Margherita Pizza' }.price", is(9.50f))
                    .body("menuItems.find { it.name == 'Margherita Pizza' }.category", is("MAIN_COURSE"))
                    .body("menuItems.find { it.name == 'Margherita Pizza' }.availability", is(true))
                    .body("menuItems.find { it.name == 'Margherita Pizza' }.imageUrl", is("https://example.com/images/margherita-pizza.jpg"))
                    .body("menuItems.find { it.name == 'Margherita Pizza' }.dietaryInfo", is("VEGETARIAN"))

                    // Validate the "Tomato Soup" item properties
                    .body("menuItems.find { it.name == 'Tomato Soup' }.id", notNullValue())
                    .body("menuItems.find { it.name == 'Tomato Soup' }.description", is("Creamy tomato soup served with crusty bread."))
                    .body("menuItems.find { it.name == 'Tomato Soup' }.price", is(4.75f))
                    .body("menuItems.find { it.name == 'Tomato Soup' }.category", is("BREAKFAST"))
                    .body("menuItems.find { it.name == 'Tomato Soup' }.availability", is(true))
                    .body("menuItems.find { it.name == 'Tomato Soup' }.imageUrl", is("https://example.com/images/tomato-soup.jpg"))
                    .body("menuItems.find { it.name == 'Tomato Soup' }.dietaryInfo", is("VEGAN"));

        }

        @Test
        void shouldReturnBadRequestForInvalidPayload(){

            var payload  = """
                    {
                      "restaurantId": null,
                      "name": "",
                      "description": "BRuch menu with beautiful chiken"
                    
                    }
                   
                    """;

            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/menus")
                    .then()
                    .statusCode(400)
                    .body("errors", containsInAnyOrder(
                            "Restaurant ID cannot be null",
                            "Menu name cannot be empty"
                    ));
        }
    }

    @Nested
    class UpdateMenuTests{

        @Test
        void shouldUpdateMenuSuccessfully() {

            var menuId = 1L;

            var payload = """
                    {
                      "name": "Updated Menu Name",
                      "description": "Updated description for the menu."
                    }
                    
                    """;

            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .put("/api/menus/{menuId}", menuId)
                    .then()
                    .statusCode(200)
                    .body("id", notNullValue())
                    .body("name", is("Updated Menu Name"))
                    .body("description", is("Updated description for the menu."));
        }

        @Test
        void shouldUpdateAllMenuItemPricesSuccessfully() {
            Long menuId = 1L;
            BigDecimal percentage = new  BigDecimal("10.0");

            RestAssured.given()
                    .when()
                    .patch("/api/menus/{menuId}/items?percentage={percentage}", menuId, percentage)
                    .then()
                    .statusCode(204);

            // Verify that the prices of all menu items in the menu have been updated correctly
            RestAssured.given()
                    .when()
                    .get("/api/menu-items/menu/{menuId}", menuId)
                    .then()
                    .statusCode(200)
                    .body("size()", is(21))
                    .body("find { it.name == 'Butter Chicken' }.price", is(13.75f)) // 12.50 + 10% = 13.75
                    .body("find { it.name == 'Paneer Tikka' }.price", is(12.10f)) // 11.00 + 10% = 12.10
                    .body("find { it.name == 'Garlic Naan' }.price", is(3.85f)) // 3.50 + 10% = 3.85
                    .body("find { it.name == 'Chana Masala' }.price", is(10.45f)); // 9.50 + 10% = 10.45
        }

        @Test
        void shouldReturnNotFoundForNonExistingMenu() {
            var menuId = 999L;

            var payload = """
                    {
                      "name": "Updated Menu Name",
                      "description": "Updated description for the menu."
                    }
                    
                    """;

            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .put("/api/menus/{menuId}", menuId)
                    .then()
                    .statusCode(404)
                    .body("detail", is("Menu with id " + menuId + " not found."));

        }
    }

    @Nested
    class DeletedMenuTests {

        @Test
        void shouldDeleteMenuSuccessfully() {
            var menuId = 51L;

            RestAssured.given()
                    .when()
                    .delete("/api/menus/{menuId}", menuId)
                    .then()
                    .statusCode(204);

            // Verify that the menu is actually deleted

            RestAssured.given()
                    .when()
                    .get("/api/menus/{menuId}", menuId)
                    .then()
                    .statusCode(404)
                    .body("detail", is("Menu with id " + menuId + " not found."));


            // Verify that the menu items associated with the deleted menu are also deleted
            RestAssured.given()
                    .when()
                    .get("/api/menu-items/menu/{menuId}", menuId)
                    .then()
                    .statusCode(200)
                    .body("size()", is(0));
        }
    }

    @Nested
    class GetMenuTests {

        @Test
        void shouldGetMenuByIdSuccessfully() {
            var menuId = 1L;

            RestAssured.given()
                    .when()
                    .get("/api/menus/{menuId}", menuId)
                    .then()
                    .statusCode(200)
                    .body("id", notNullValue())
                    .body("name", is("Main Menu"))
                    .body("description", is("The core selection for Spice Hub"))
                    .body("menuItems.size()", is(21));
        }

        @Test
        void shouldReturnNotFoundForNonExistingMenu() {
            var menuId = 999L;

            RestAssured.given()
                    .when()
                    .get("/api/menus/{menuId}", menuId)
                    .then()
                    .statusCode(404)
                    .body("detail", is("Menu with id " + menuId + " not found."));
        }

        @Test
        void shouldGetMenusByRestaurantIdSuccessfully() {
            var restaurantId = 51L;

            RestAssured.given()
                    .when()
                    .get("/api/menus/restaurant/{restaurantId}", restaurantId)
                    .then()
                    .statusCode(200)
                    .body("size()", is(2))
                    .body("name", containsInAnyOrder("Main Menu", "Brunch"));
        }

        @Test
        void shouldReturnRestaurantNotFoundForInvalidRestaurantId() {
            var restaurantId = 999L;

            RestAssured.given()
                    .when()
                    .get("/api/menus/restaurant/{restaurantId}", restaurantId)
                    .then()
                    .statusCode(404)
                    .body("detail", is("Restaurant with ID " + restaurantId + " not found."));
        }

        @Test
        void shouldReturnEmptyListForRestaurantWithNoMenus() {
            // Post a new restaurant without any menus



            var restaurantId = 251L; // Assuming this restaurant exists but has no menus

            RestAssured.given()
                    .when()
                    .get("/api/menus/restaurant/{restaurantId}", restaurantId)
                    .then()
                    .statusCode(200)
                    .body("size()", is(0));
        }









    }


}