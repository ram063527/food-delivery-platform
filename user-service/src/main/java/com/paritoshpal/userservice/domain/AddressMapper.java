package com.paritoshpal.userservice.domain;

import com.paritoshpal.userservice.domain.models.AddressResponse;
import com.paritoshpal.userservice.domain.models.AddressUpdateRequest;
import com.paritoshpal.userservice.domain.models.CreateAddressRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
interface AddressMapper {


    // Request -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    AddressEntity toEntity(CreateAddressRequest request);

    // Entity -> Response
    AddressResponse toResponse(AddressEntity entity);



    // 1. IGNORE nulls so we don't wipe out existing data
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    // 2. Protect the ID and Relationship
    @Mapping(target = "id", ignore = true)       // ID never changes
    @Mapping(target = "user", ignore = true)     // Address ownership shouldn't change here
    @Mapping(target = "createdAt", ignore = true)// Creation time never changes
    @Mapping(target = "updatedAt", ignore = true)// Handled by @UpdateTimestamp
    void updateAddressFromRequest(AddressUpdateRequest request, @MappingTarget AddressEntity entity);

}
