package com.paritoshpal.userservice.domain;

import com.paritoshpal.userservice.domain.exceptions.AddressNotFoundException;
import com.paritoshpal.userservice.domain.exceptions.UserNotFoundException;
import com.paritoshpal.userservice.domain.models.AddressResponse;
import com.paritoshpal.userservice.domain.models.AddressUpdateRequest;
import com.paritoshpal.userservice.domain.models.CreateAddressRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private static final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;

    @Override
    public AddressResponse createAddress(CreateAddressRequest request) {
        // 1. Convert to Entity
        AddressEntity addressEntity = addressMapper.toEntity(request);
        // Get CurrentLogged in User :
        UserEntity userEntity = userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.userId()));
        addressEntity.setUser(userEntity);
        // 2. Save Entity
        AddressEntity savedAddress = addressRepository.save(addressEntity);
        log.info("Created address with id: {} for user id: {}", savedAddress.getId(), request.userId());
        // 3. Convert to Response
        return addressMapper.toResponse(savedAddress);
    }

    @Override
    public AddressResponse getAddressById(Long addressId) {
        AddressEntity addressEntity = addressRepository.findById(addressId)
                .orElseThrow(() -> AddressNotFoundException.forId(addressId));
        return addressMapper.toResponse(addressEntity);
    }

    @Override
    public AddressResponse updateAddress(Long addressId, AddressUpdateRequest request) {
        // 1. Find existing address
        AddressEntity existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> AddressNotFoundException.forId(addressId));
        // 2. Update fields
        addressMapper.updateAddressFromRequest(request, existingAddress);
        // 3. Save updated address
        AddressEntity updatedAddress = addressRepository.save(existingAddress);
        log.info("Updated address with id: {}", addressId);
        // 4. Convert to Response
        return addressMapper.toResponse(updatedAddress);

    }

    @Override
    public void deleteAddress(Long addressId) {
        AddressEntity addressEntity = addressRepository.findById(addressId)
                .orElseThrow(() -> AddressNotFoundException.forId(addressId));
        addressRepository.delete(addressEntity);
        log.info("Deleted address with id: {}", addressId);
    }

    @Override
    public List<AddressResponse> getAddressesByUserEmail(String email) {
        List<AddressEntity> addressEntities = addressRepository.findByUserEmail(email);
        log.info("Found {} addresses for user with email: {}", addressEntities.size(), email);
        return addressEntities.stream().map(addressMapper::toResponse).toList();
    }

    @Override
    public List<AddressResponse> getAddressesByUserId(Long userId) {
        if(!userRepository.existsById(userId)){
            throw  UserNotFoundException.forId(userId);
        }
        List<AddressEntity> addressEntities = addressRepository.findByUserId(userId);
        log.info("Found {} addresses for user with id: {}", addressEntities.size(), userId);
        return addressEntities.stream().map(addressMapper::toResponse).toList();
    }


}
