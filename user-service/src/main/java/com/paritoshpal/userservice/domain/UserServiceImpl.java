package com.paritoshpal.userservice.domain;

import com.paritoshpal.userservice.domain.exceptions.EmailAlreadyInUseException;
import com.paritoshpal.userservice.domain.exceptions.UserNotFoundException;
import com.paritoshpal.userservice.domain.models.CreateUserRequest;
import com.paritoshpal.userservice.domain.models.UpdateUserRequest;
import com.paritoshpal.userservice.domain.models.UserResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserResponse createUser(CreateUserRequest request) {
        // 1. Convert to Entity
        UserEntity userEntity = userMapper.toEntity(request);
        // 2. Add ignored fields from the mapper
        userEntity.setPassword(passwordEncoder.encode(request.password()));
        // 3. Validate Uniqueness
        if(userRepository.existsByEmail(request.email())){
            throw EmailAlreadyInUseException.forEmail(request.email());
        }
        // 3. Save Entity
        UserEntity savedUser = userRepository.save(userEntity);
        log.info("Created user with id: {}", savedUser.getId());
        // 4. Convert to Response
        return userMapper.toResponse(savedUser);

    }

    @Override
    public UserResponse getUserById(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.forId(userId));
        return userMapper.toResponse(userEntity);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> UserNotFoundException.forEmail(email));
        return userMapper.toResponse(userEntity);
    }

    @Override
    public UserResponse updateUser(Long userId, UpdateUserRequest request) {
        // For now passing the userId in the request Itself, But after authentication
        // logged in user will be fetched from the security context and that will be used for update instead of passing userId in the request

        // 1. Find Existing User
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.forId(userId));
        // 2. Update Fields
        userMapper.updateUserFromRequest(request, userEntity);
        // 3. Save Updated User
        UserEntity updatedUser = userRepository.save(userEntity);
        log.info("Updated user with id: {}", updatedUser.getId());
        // 4. Convert to Response
        return userMapper.toResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.forId(userId));
        userRepository.delete(userEntity);
        log.info("Deleted user with id: {}", userId);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<UserResponse> users = userRepository.findAll().stream().map(userMapper::toResponse).toList();
        log.info("Found {} users", users.size());
        return users;
    }

    @Override
    public List<UserResponse> getUsersByRole(Role role) {
       List<UserEntity> userEntities = userRepository.findAllByRole(role);
       log.info("Found {} users", userEntities.size());
         return userEntities.stream().map(userMapper::toResponse).collect(Collectors.toList());
    }
}
