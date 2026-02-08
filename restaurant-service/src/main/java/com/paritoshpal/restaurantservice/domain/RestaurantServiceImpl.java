package com.paritoshpal.restaurantservice.domain;

import com.paritoshpal.restaurantservice.ApplicationProperties;
import com.paritoshpal.restaurantservice.clients.user.Role;
import com.paritoshpal.restaurantservice.clients.user.UserResponse;
import com.paritoshpal.restaurantservice.clients.user.UserServiceClient;
import com.paritoshpal.restaurantservice.domain.exceptions.EmailAlreadyInUseException;
import com.paritoshpal.restaurantservice.domain.exceptions.InvalidOwnerException;
import com.paritoshpal.restaurantservice.domain.exceptions.OwnerNotFoundException;
import com.paritoshpal.restaurantservice.domain.exceptions.RestaurantNotFoundException;
import com.paritoshpal.restaurantservice.domain.mapper.RestaurantMapper;
import com.paritoshpal.restaurantservice.domain.models.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final ApplicationProperties applicationProperties;
    private final UserServiceClient client;


    @Override
    public RestaurantResponse createRestaurant(CreateRestaurantRequest request) {
        // 1. Convert CreateRestaurantRequest to Restaurant entity
        RestaurantEntity restaurantEntity = restaurantMapper.toRestaurantEntity(request);
        // Check if the restaurant with same email already exists
        restaurantRepository.findByEmail(restaurantEntity.getEmail())
                .ifPresent(r -> {
                    throw  EmailAlreadyInUseException.forEmail(request.email());
                });
        // Check User Service if the ownerId is valid or not
        UserResponse user = client.getUserById(request.ownerId())
                .orElseThrow(()-> OwnerNotFoundException.withId(request.ownerId()));

        // 4. Role Verification
        if (user.role() != Role.RESTAURANT_OWNER) {
            throw  InvalidOwnerException.unAuthorizedRole(request.ownerId(), user.role().name());
        }


        // 2. Save the Restaurant entity to the database
        RestaurantEntity savedRestaurant = restaurantRepository.save(restaurantEntity);
        // 3. Convert the saved Restaurant entity to RestaurantResponse and return
        return restaurantMapper.toRestaurantResponse(savedRestaurant);
    }

    @Override
    public RestaurantResponse updateRestaurant(Long restaurantId, RestaurantUpdateRequest request) {
        // 1. Fetch the existing Restaurant entity by ID
        RestaurantEntity existingRestaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> RestaurantNotFoundException.forId(restaurantId));
        // 2. Update the existing Restaurant entity with values from the request
        restaurantMapper.updateRestaurantFromRequest(request, existingRestaurant);
        // 3. Save the updated Restaurant entity to the database
        RestaurantEntity updatedRestaurant = restaurantRepository.save(existingRestaurant);
        // 4. Convert the updated Restaurant entity to RestaurantResponse and return
        return restaurantMapper.toRestaurantResponse(updatedRestaurant);
    }

    @Override
    public RestaurantResponse updateRestaurantStatus(Long restaurantId, Status status) {
        // 1. Fetch the existing Restaurant entity by ID
        RestaurantEntity existingRestaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> RestaurantNotFoundException.forId(restaurantId));
        // 2. Update the status of the existing Restaurant entity
        existingRestaurant.setStatus(status);
        // 3. Save the updated Restaurant entity to the database
        RestaurantEntity updatedRestaurant = restaurantRepository.save(existingRestaurant);
        // 4. Convert the updated Restaurant entity to RestaurantResponse and return
        return restaurantMapper.toRestaurantResponse(updatedRestaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) {
        // 1. Check if the Restaurant entity exists by ID
        if (!restaurantRepository.existsById(restaurantId)) {
            throw RestaurantNotFoundException.forId(restaurantId);
        }
        // 2. Delete the Restaurant entity from the database
        restaurantRepository.deleteById(restaurantId);
    }

    @Override
    public RestaurantDetailedResponse getRestaurantById(Long restaurantId) {
        // 1. Fetch the Restaurant entity by ID
        RestaurantEntity restaurantEntity = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> RestaurantNotFoundException.forId(restaurantId));
        // 2. Convert the Restaurant entity to RestaurantDetailedResponse and return
        return restaurantMapper.toRestaurantDetailedResponse(restaurantEntity);
    }

    @Override
    public PageResult<RestaurantResponse> getAllRestaurants(int pageNo) {
        Sort sort = Sort.by("name").ascending();
        pageNo = pageNo <= 1 ? 0 : pageNo - 1;
        Pageable pageable = PageRequest.of(pageNo, applicationProperties.pageSize(), sort);
        Page<RestaurantResponse> restaurantPage = restaurantRepository.findAll(pageable)
                .map(restaurantMapper::toRestaurantResponse);

        return new PageResult<>(
                restaurantPage.getContent(),
                (int) restaurantPage.getTotalElements(),
                restaurantPage.getNumber() + 1,
                restaurantPage.getTotalPages(),
                restaurantPage.isFirst(),
                restaurantPage.isLast(),
                restaurantPage.hasNext(),
                restaurantPage.hasPrevious()
        );
    }

    @Override
    public List<RestaurantResponse> getRestaurantsByOwnerId(Long ownerId) {
        // Later we will make call to the User Service to check if the ownerId is valid or not,
        // For now we will just fetch the restaurants by ownerId and return the response

         return restaurantRepository.findByOwnerId(ownerId).stream()
                .map(restaurantMapper::toRestaurantResponse)
                .toList();
    }

    @Override
    public List<RestaurantResponse> getRestaurantsByName(String name) {
        return restaurantRepository.findByNameIgnoreCase(name).stream()
                .map(restaurantMapper::toRestaurantResponse)
                .toList();
    }

    @Override
    public List<RestaurantResponse> getRestaurantsByCuisine(String cuisine) {
        return restaurantRepository.findByCuisineIgnoreCase(cuisine).stream()
                .map(restaurantMapper::toRestaurantResponse)
                .toList();
    }

    @Override
    public List<RestaurantResponse> getRestaurantsByCity(String city) {
        return restaurantRepository.findByAddress_CityIgnoreCase(city).stream()
                .map(restaurantMapper::toRestaurantResponse)
                .toList();
    }

    @Override
    public PageResult<RestaurantResponse> searchRestaurants(String query, String name, String cuisine, String city, int pageNo) {
        Sort sort = Sort.by("name").ascending();
        pageNo = pageNo <= 1 ? 0 : pageNo - 1;
        Pageable pageable = PageRequest.of(pageNo, applicationProperties.pageSize(), sort);

        // Build Dynamic Specification :
        Specification<RestaurantEntity> spec = Specification.unrestricted();

        if(query != null && !query.isBlank()) {
            String pattern = "%" + query.toLowerCase() + "%";
            spec = spec.and((root, q, cb) -> cb.or(
                    cb.like(cb.lower(root.get("name")), pattern),
                    cb.like(cb.lower(root.get("description")), pattern)
            ));
        }
        if(name != null && !name.isBlank()) {
            spec = spec.and((root,q,cb)-> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if(cuisine != null && !cuisine.isBlank()) {
            spec = spec.and((root,q,cb)-> cb.like(cb.lower(root.get("cuisine")), "%" + cuisine.toLowerCase() + "%"));
        }
        if (city != null && !city.isBlank()) {
            // FIXED: Access city via the 'address' association
            spec = spec.and((root, q, cb) -> cb.equal(cb.lower(root.join("address").get("city")), city.toLowerCase()));
        }

        Page<RestaurantResponse> restaurantPage = restaurantRepository.findAll(spec, pageable)
                .map(restaurantMapper::toRestaurantResponse);

        return new PageResult<>(
                restaurantPage.getContent(),
                (int) restaurantPage.getTotalElements(),
                restaurantPage.getNumber() + 1,
                restaurantPage.getTotalPages(),
                restaurantPage.isFirst(),
                restaurantPage.isLast(),
                restaurantPage.hasNext(),
                restaurantPage.hasPrevious()
        );


    }
}
