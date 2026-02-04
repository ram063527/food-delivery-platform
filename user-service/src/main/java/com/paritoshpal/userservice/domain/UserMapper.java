package com.paritoshpal.userservice.domain;

import com.paritoshpal.userservice.domain.models.CreateUserRequest;
import com.paritoshpal.userservice.domain.models.UpdateUserRequest;
import com.paritoshpal.userservice.domain.models.UserResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
interface UserMapper {

    // Request -> Entity

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true) // Intialized empty set
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "password", ignore = true) // Password Will Be Set In Service Layer After Encoding
    @Mapping(target = "status", constant = "ACTIVE") // Default New User To Active
    UserEntity toEntity(CreateUserRequest request);


    // Entity -> Response
    UserResponse toResponse(UserEntity entity);


    // Update : DTO -> Existing Entity

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true) // Email Should Not Be Updated
    @Mapping(target = "password", ignore = true) // Password Should Not Be Updated Here
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "role", ignore = true) // Role Should Not Be Updated Here
    @Mapping(target = "status", ignore = true) // Status Should Not Be Updated Here
    @Mapping(target = "addresses", ignore = true) // Addresses Should Not Be Updated Here
    void updateUserFromRequest(UpdateUserRequest request, @MappingTarget UserEntity entity);
}
