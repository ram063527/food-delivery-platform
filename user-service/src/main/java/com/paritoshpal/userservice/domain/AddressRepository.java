package com.paritoshpal.userservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    List<AddressEntity> findByUserEmail(String email);

    List<AddressEntity> findByUserId(Long userId);
}
