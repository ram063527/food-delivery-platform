package com.paritoshpal.orderservice.clients.user;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api/address")
public interface UserServiceClient {

    @GetExchange("/id/{id}")
    AddressResponse getAddressById(
            @PathVariable Long id
    );
}
