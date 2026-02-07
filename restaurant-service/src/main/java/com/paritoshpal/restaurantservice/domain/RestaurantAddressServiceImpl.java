package com.paritoshpal.restaurantservice.domain;

import com.paritoshpal.restaurantservice.domain.exceptions.RestaurantAddressAlreadyExistsException;
import com.paritoshpal.restaurantservice.domain.exceptions.RestaurantAddressNotFoundException;
import com.paritoshpal.restaurantservice.domain.exceptions.RestaurantNotFoundException;
import com.paritoshpal.restaurantservice.domain.mapper.RestaurantAddressMapper;
import com.paritoshpal.restaurantservice.domain.models.CreateRestaurantAddressRequest;
import com.paritoshpal.restaurantservice.domain.models.RestaurantAddressResponse;
import com.paritoshpal.restaurantservice.domain.models.RestaurantAddressUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantAddressServiceImpl implements  RestaurantAddressService

{

    private final RestaurantAddressRepository restaurantAddressRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantAddressMapper restaurantAddressMapper;

    @Override
    public RestaurantAddressResponse createRestaurantAddress(CreateRestaurantAddressRequest request) {
        // 1. Validate the request (e.g., check if restaurant exists, validate address fields)
        RestaurantEntity restaurantEntity = restaurantRepository.findById(request.restaurantId())
                .orElseThrow(() -> RestaurantNotFoundException.forId(request.restaurantId()));

        restaurantAddressRepository.findByRestaurantId(restaurantEntity.getId())
                .ifPresent(a -> {
                    throw RestaurantAddressAlreadyExistsException.forRestaurantId(request.restaurantId());
                });

        // 2. Map the request to an entity
        RestaurantAddressEntity restaurantAddressEntity = restaurantAddressMapper.toRestaurantAddressEntity(request);
        restaurantAddressEntity.setRestaurant(restaurantEntity);

        // 3. Save the entity to the database
        RestaurantAddressEntity savedEntity = restaurantAddressRepository.save(restaurantAddressEntity);
        // 4. Map the saved entity to a response DTO
        return restaurantAddressMapper.toAddressResponse(savedEntity);

    }

    @Override
    public RestaurantAddressResponse updateRestaurantAddress(Long restaurantAddressId, RestaurantAddressUpdateRequest request) {
        // 1. Validate the request (e.g., check if restaurant address exists, validate address fields)
        RestaurantAddressEntity restaurantAddressEntity = restaurantAddressRepository.findById(restaurantAddressId)
                .orElseThrow(() -> RestaurantAddressNotFoundException.forAddressId(restaurantAddressId));

        // 2. Map the update request to the existing entity
        restaurantAddressMapper.updateRestaurantAddressFromRequest(request, restaurantAddressEntity);

        // 3. Save the updated entity to the database
        RestaurantAddressEntity updatedEntity = restaurantAddressRepository.save(restaurantAddressEntity);

        // 4. Map the updated entity to a response DTO
        return restaurantAddressMapper.toAddressResponse(updatedEntity);
    }

    @Override
    public void deleteRestaurantAddress(Long restaurantAddressId) {
       if(!restaurantAddressRepository.existsById(restaurantAddressId)) {
           throw new RestaurantAddressNotFoundException("Restaurant address not found with ID: " + restaurantAddressId);
       }
         restaurantAddressRepository.deleteById(restaurantAddressId);
    }

    @Override
    public void deleteRestaurantAddressByRestaurantId(Long restaurantId) {
        RestaurantAddressEntity address = restaurantAddressRepository.findByRestaurantId(restaurantId)
                .orElseThrow(() -> RestaurantAddressNotFoundException.forRestaurantId(restaurantId));
        restaurantAddressRepository.delete(address);
    }

    @Override
    public RestaurantAddressResponse getAddressByRestaurantId(Long restaurantId) {
        RestaurantAddressEntity restaurantAddressEntity = restaurantAddressRepository.findByRestaurantId(restaurantId)
                .orElseThrow(() -> RestaurantAddressNotFoundException.forRestaurantId(restaurantId));
        return restaurantAddressMapper.toAddressResponse(restaurantAddressEntity);
    }

    @Override
    public RestaurantAddressResponse getRestaurantAddressById(Long restaurantAddressId) {
        RestaurantAddressEntity restaurantAddressEntity = restaurantAddressRepository.findById(restaurantAddressId)
                .orElseThrow(() -> RestaurantAddressNotFoundException.forAddressId(restaurantAddressId));
        return restaurantAddressMapper.toAddressResponse(restaurantAddressEntity);

    }


}
