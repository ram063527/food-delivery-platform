
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
            Long restaurantId = 1L;
            var payload  = """
                    {
                      
                      "name": "BRUNCH",
                      "description": "BRuch menu with beautiful chiken"
                    
                    }
                   
                    """;

            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/restaurants/{restaurantId}/menus", restaurantId)
                    .then()
                    .statusCode(201)
                    .body("id",notNullValue())
                    .body("name",is("BRUNCH"))
                    .body("description",is("BRuch menu with beautiful chiken"));

        }

        @Test
        void shouldCreateMenuWithMenuItemsSuccessfully() {

            Long restaurantId = 51L;
            var payload = """
                    {
                      
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
                    .post("/api/restaurants/{restaurantId}/menus",restaurantId)
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
            Long restaurantId = 1L;
            var payload  = """
                    {
                     
                      "name": "",
                      "description": "BRuch menu with beautiful chiken"
                    
                    }
                   
                    """;

            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/restaurants/{restaurantId}/menus",restaurantId)
                    .then()
                    .statusCode(400)
                    .body("errors", containsInAnyOrder(

                            "Menu name cannot be empty"
                    ));
        }



        @Test
        void shouldReturnNotFoundForNonExistingRestaurant() {
            Long restaurantId = 999L; // Non-existent restaurant
            var payload = """
                {
                  "name": "BRUNCH",
                  "description": "Brunch menu with beautiful chicken"
                }
                """;

            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/restaurants/{restaurantId}/menus", restaurantId)
                    .then()
                    .statusCode(404)
                    .body("detail", is("Restaurant with ID " + restaurantId + " not found."));
        }




    }

    @Nested
    class UpdateMenuTests{

        @Test
        void shouldUpdateMenuSuccessfully() {

            var menuId = 1L;
            Long restaurantId = 1L;

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
                    .put("/api/restaurants/{restaurantId}/menus/{id}",restaurantId, menuId)
                    .then()
                    .statusCode(200)
                    .body("id", notNullValue())
                    .body("name", is("Updated Menu Name"))
                    .body("description", is("Updated description for the menu."));
        }

        @Test
        void shouldUpdateAllMenuItemPricesSuccessfully() {
            Long menuId = 1L;
            Long restaurantId = 1L;
            BigDecimal percentage = new  BigDecimal("10.0");

            RestAssured.given()
                    .when()
                    .patch("api/restaurants/{restaurantId}/menus/{id}/prices?percentage={percentage}", restaurantId, menuId, percentage)
                    .then()
                    .statusCode(204);

            // Verify that the prices of all menu items in the menu have been updated correctly

            RestAssured.given()
                    .when()
                    .get("/api/restaurants/{restaurantId}/menus/{id}", restaurantId,menuId)
                    .then()
                    .statusCode(200)
                    .body("menuItems.size()", is(21));
                    // Add assertions for the remaining menu items

        }

        @Test
        void shouldReturnNotFoundForNonExistingMenu() {
            var menuId = 999L;
            Long restaurantId = 1L;
            BigDecimal percentage = new  BigDecimal("10.0");


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
                    .put("api/restaurants/{restaurantId}/menus/{id}?percentage={percentage}",restaurantId,menuId,percentage)
                    .then()
                    .statusCode(404)
                    .body("detail", is("Menu with id " + menuId + " not found for restaurant with id " + restaurantId + "."));

        }


        @Test
        void shouldReturnNotFoundWhenUpdatingNonExistingMenu() {
            Long restaurantId = 1L;
            Long menuId = 999L; // Non-existent menu

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
                    .put("/api/restaurants/{restaurantId}/menus/{id}", restaurantId, menuId)
                    .then()
                    .statusCode(404)
                    .body("detail", is("Menu with id " + menuId + " not found for restaurant with id " + restaurantId + "."));
        }
    }

    @Nested
    class DeletedMenuTests {

        @Test
        void shouldDeleteMenuSuccessfully() {
            Long restaurantId = 51L;
            var menuId = 201L;

            RestAssured.given()
                    .when()
                    .delete("/api/restaurants/{restaurantId}/menus/{id}",restaurantId, menuId)
                    .then()
                    .statusCode(204);

            // Verify that the menu is actually deleted

            RestAssured.given()
                    .when()
                    .get("/api/restaurants/{restaurantId}/menus/{id}", restaurantId, menuId)
                    .then()
                    .statusCode(404)
                    .body("detail", is("Menu with id " + menuId + " not found for restaurant with id " + restaurantId + "."));
        }

        @Test
        void shouldReturnNotFoundWhenDeletingNonExistingMenu() {
            Long restaurantId = 1L;
            Long menuId = 999L; // Non-existent menu

            RestAssured.given()
                    .when()
                    .delete("/api/restaurants/{restaurantId}/menus/{id}", restaurantId, menuId)
                    .then()
                    .statusCode(404)
                    .body("detail", is("Menu with id " + menuId + " not found."));
        }


    }

    @Nested
    class GetMenuTests {

        @Test
        void shouldGetMenuByIdSuccessfully() {
            Long restaurantId = 1L;
            var menuId = 1L;

            RestAssured.given()
                    .when()
                    .get("/api/restaurants/{restaurantId}/menus/{id}",restaurantId, menuId)
                    .then()
                    .statusCode(200)
                    .body("id", notNullValue())
                    .body("name", is("Main Menu"))
                    .body("description", is("The core selection for Spice Hub"))
                    .body("menuItems.size()", is(21));
        }

        @Test
        void shouldReturnNotFoundForNonExistingMenu() {
            Long restaurantId = 1L;
            var menuId = 999L;

            RestAssured.given()
                    .when()
                    .get("/api/restaurants/{restaurantId}/menus/{id}",restaurantId, menuId)
                    .then()
                    .statusCode(404)
                    .body("detail", is("Menu with id " + menuId + " not found for restaurant with id " + restaurantId + "."));
        }

        @Test
        void shouldGetMenusByRestaurantIdSuccessfully() {
            var restaurantId = 1L;

            RestAssured.given()
                    .when()
                    .get("/api/restaurants/{restaurantId}/menus", restaurantId)
                    .then()
                    .statusCode(200)
                    .body("size()", is(3))
                    .body("name", containsInAnyOrder("Main Menu", "Breakfast","Drinks"));
        }

        @Test
        void shouldReturnRestaurantNotFoundForInvalidRestaurantId() {
            var restaurantId = 999L;

            RestAssured.given()
                    .when()
                    .get("/api/restaurants/{restaurantId}/menus", restaurantId)
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
                    .get("/api/restaurants/{restaurantId}/menus", restaurantId)
                    .then()
                    .statusCode(200)
                    .body("size()", is(0));
        }









    }


}