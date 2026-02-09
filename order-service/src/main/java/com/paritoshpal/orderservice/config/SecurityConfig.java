package com.paritoshpal.orderservice.config;

import org.springframework.stereotype.Service;

@Service
public class SecurityConfig {

    public Long getLoginUserId(){
        return 1L;
    }
}
