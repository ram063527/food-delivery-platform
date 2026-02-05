package com.paritoshpal.userservice.web.controller;

import com.paritoshpal.userservice.AbstractIT;
import com.paritoshpal.userservice.domain.models.UserResponse;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@Sql("/test_data.sql")
class UserControllerTest extends AbstractIT {

    @Nested
    class CreateUserTests {

        @Test
        void shouldCreateUserSuccessfully() {
            var payload = """
                    {
                      "email": "samruddhi@example.com",
                      "firstName": "Samruddhi",
                      "lastName": "Pal",
                      "phone": "555-9876",
                      "password": "newuserpassword123",
                      "role": "CUSTOMER"
                    }
                    
                    """;

            given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/users")
                    .then()
                    .statusCode(201)
                    .body("email", equalTo("samruddhi@example.com"))
                    .body("firstName", equalTo("Samruddhi"))
                    .body("lastName", equalTo("Pal"))
                    .body("phone", equalTo("555-9876"))
                    .body("role", equalTo("CUSTOMER"))
                    .body("id", notNullValue());

        }

        @Test
        void shouldReturnBadRequestWhenMandatoryFieldsAreMissing() {
            var payload = """
                    {
                      "email": "",
                      "firstName": "",
                      "lastName": "",
                      "password": "",
                      "role": null
                    }
                    
                    """;

            given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/users")
                    .then()
                    .statusCode(400)
                    .body("errors", hasItems(
                            "Email cannot be empty",
                            "First name cannot be empty",
                            "Last name cannot be empty",
                            "Password cannot be empty",
                            "Role cannot be null"
                    ))
                    .body("errors", hasSize(5));

        }


    }

    @Nested
    class UpdateUserTests {

        @Test
        void shouldUpdateFirstNameSuccessfully() {
            var payload = """
                    {
                      "firstName": "UpdatedFirstName"
                    }
                    
                    """;

            given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .put("/api/users/1")
                    .then()
                    .statusCode(200)
                    .body("firstName", equalTo("UpdatedFirstName"));
        }

        @Test
        void shoudUpdateLastNameSuccessfully() {
            var payload = """
                    {
                      "lastName": "UpdatedLastName"
                    }
                    
                    """;

            given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .put("/api/users/1")
                    .then()
                    .statusCode(200)
                    .body("lastName", equalTo("UpdatedLastName"));
        }

        @Test
        void shouldUpdatePhoneSuccessfully() {
            var payload = """
                    {
                      "phone": "555-0000"
                    }
                    
                    """;

            given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .put("/api/users/1")
                    .then()
                    .statusCode(200)
                    .body("phone", equalTo("555-0000"));
        }

        @Test
        void shouldMakeNoChangesWhenNoFieldsAreProvided() {
            var payload = """
                    {
                      
                    }
                    
                    """;

            given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .put("/api/users/1")
                    .then()
                    .statusCode(200)
                    .body("firstName", equalTo("John"))
                    .body("lastName", equalTo("Doe"))
                    .body("phone", equalTo("1234567890"))
                    .body("email", equalTo("john.doe@example.com"))
                    .body("role", equalTo("CUSTOMER"));
        }

        @Test
        void shouldReturnNotFoundWhenUserDoesNotExist() {
             String nonExistantUserId = "999";
            var payload = """
                    {
                      "firstName": "NonExistent"
                    }
                    
                    """;

            given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .put("/api/users/{userId}", nonExistantUserId)
                    .then()
                    .statusCode(404)
                    .body("detail", equalTo("User with id 999 not found"));

        }

    }

    @Nested
    class GetUsersTests {

        @Test
        void shouldGetAllUsersSuccessfully() {
            List<UserResponse> users = given()
                    .when()
                    .get("/api/users")
                    .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .as(new TypeRef<>() {
                    });

            assertEquals(5, users.size());
        }

        @Test
        void shouldSuccessfullyGetUserById() {
            given()
                    .when()
                    .get("/api/users/id/{id}", 1)
                    .then()
                    .statusCode(200)
                    .body("id", notNullValue())
                    .body("email", equalTo("john.doe@example.com"))
                    .body("firstName", equalTo("John"))
                    .body("lastName", equalTo("Doe"));
        }

        @Test
        void shouldReturnNotFoundWhenGettingUserByNonExistentId() {
            String nonExistentUserId = "999";
            given()
                    .when()
                    .get("/api/users/id/{id}", nonExistentUserId)
                    .then()
                    .statusCode(404)
                    .body("detail", equalTo("User with id 999 not found"));
        }

        @Test
        void shouldSuccessfullyGetUserByEmail() {
            String email = "john.doe@example.com";
            given()
                    .when()
                    .get("/api/users/email/{email}", email)
                    .then()
                    .statusCode(200)
                    .body("id", notNullValue())
                    .body("email", equalTo(email));
        }

        @Test
        void shouldReturnNotFoundWhenGettingUserByNonExistentEmail() {
            String nonExistentEmail = "something@exmaple.com";
            given()
                    .when()
                    .get("/api/users/email/{email}", nonExistentEmail)
                    .then()
                    .statusCode(404)
                    .body("detail", equalTo("User with email "+nonExistentEmail +" not found"));
        }

        @Test
        void shouldSuccessfullyGetUsersByRole() {
            String role = "CUSTOMER";
            List<UserResponse> users = given()
                    .when()
                    .get("/api/users/role/{role}", role)
                    .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .as(new TypeRef<>() {
                    });

            assertEquals(2, users.size());
        }

         @Test
         void shouldReturnBadRequestWhenRoleNotInList() {
             String role = "ADMIN@1234";
              given()
                     .when()
                     .get("/api/users/role/{role}", role)
                     .then()
                     .statusCode(400);
         }



    }

    @Nested
    class DeleteUserTests {

        @Test
        void shouldDeleteUserSuccessfully() {
            given()
                    .when()
                    .delete("/api/users/{userId}", 1)
                    .then()
                    .statusCode(204);

            // Verify user is deleted
            given()
                    .when()
                    .get("/api/users/id/{id}", 1)
                    .then()
                    .statusCode(404);
        }

        @Test
        void shouldReturnNotFoundWhenDeletingNonExistentUser() {
            String nonExistentUserId = "999";
            given()
                    .when()
                    .delete("/api/users/{userId}", nonExistentUserId)
                    .then()
                    .statusCode(404)
                    .body("detail", equalTo("User with id 999 not found"));
        }


    }

}