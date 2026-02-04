package com.paritoshpal.userservice.domain;

import com.paritoshpal.userservice.domain.models.CreateUserRequest;
import com.paritoshpal.userservice.domain.models.UpdateUserRequest;
import com.paritoshpal.userservice.domain.models.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);
    UserResponse getUserById(Long userId);
    UserResponse getUserByEmail(String email);
    UserResponse updateUser(Long userId, UpdateUserRequest request);
    void deleteUser(Long userId);
    List<UserResponse> getAllUsers();
    List<UserResponse> getUsersByRole(Role role);
}
