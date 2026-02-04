package com.paritoshpal.userservice.domain;

import com.paritoshpal.userservice.domain.models.UserResponse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface UserRepository extends JpaRepository<UserEntity,Long> {


    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findAllByRole(Role role);

    boolean existsByEmail(@Email String email);
}
