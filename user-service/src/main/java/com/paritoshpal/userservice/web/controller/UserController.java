package com.paritoshpal.userservice.web.controller;

import com.paritoshpal.userservice.domain.Role;
import com.paritoshpal.userservice.domain.UserService;
import com.paritoshpal.userservice.domain.models.CreateUserRequest;
import com.paritoshpal.userservice.domain.models.UpdateUserRequest;
import com.paritoshpal.userservice.domain.models.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        log.info("Received request to get all users");
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponse> getUserById(
               @PathVariable Long id
    ) {
//        sleep();
        log.info("Received request to get user with id: {}", id);
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(
           @PathVariable String email) {
        log.info("Received request to get user with email: {}", email);
        UserResponse user = userService.getUserByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserResponse>> getUsersByRole(
            @PathVariable Role role) {

        log.info("Received request to get users with role: {}", role);
        List<UserResponse> users = userService.getUsersByRole(role);
        return ResponseEntity.status(HttpStatus.OK).body(users);

    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @RequestBody @Valid CreateUserRequest request
    ) {
        log.info("Received request to create user with email: {}", request.email());
        UserResponse createdUser = userService.createUser(request);
        log.info("Created user with id: {} and email: {}", createdUser.id(), createdUser.email());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long userId,
            @RequestBody @Valid UpdateUserRequest request
            ) {

        log.info("Received request to update user");
        UserResponse updatedUser = userService.updateUser(userId, request);
        log.info("Updated user with id: {}", updatedUser.id());
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long userId
    ) {
        log.info("Received request to delete user with id: {}", userId);
        userService.deleteUser(userId);
        log.info("Deleted user with id: {}", userId);
        return ResponseEntity.noContent().build();

    }


    private void sleep(){
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



}
