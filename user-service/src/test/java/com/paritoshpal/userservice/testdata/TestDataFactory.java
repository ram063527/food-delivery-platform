package com.paritoshpal.userservice.testdata;

import com.paritoshpal.userservice.domain.models.CreateUserRequest;
import org.instancio.Instancio;

import java.util.List;

import static org.instancio.Select.field;

public class TestDataFactory {

    static final List<String> VALID_ROLES =
            List.of("CUSTOMER",
                    "RESTAURANT_OWNER",
                    "DELIVERY_DRIVER",
                    "ADMIN");


    public static CreateUserRequest createValidUserRequest(){
        return Instancio.of(CreateUserRequest.class)
                .generate(field(CreateUserRequest::email), gen -> gen.text().pattern("#a#a#a#a#a#a@mail.com"))
                .generate(field(CreateUserRequest::role), gen -> gen.oneOf(VALID_ROLES))
                .create();
    }

    public static CreateUserRequest createUserRequestWithInvalidEmail(){
        return Instancio.of(CreateUserRequest.class)
                .generate(field(CreateUserRequest::email), gen -> gen.text().pattern("invalid-email"))
                .generate(field(CreateUserRequest::role), gen -> gen.oneOf(VALID_ROLES))
                .create();
    }

    public static CreateUserRequest createUserRequestWithInvalidRole(){
        return Instancio.of(CreateUserRequest.class)
                .generate(field(CreateUserRequest::email), gen -> gen.text().pattern("#a#a#a#a#a#a@mail.com"))
                .generate(field(CreateUserRequest::role), gen -> gen.oneOf("INVALID_ROLE"))
                .create();

    }

    public static CreateUserRequest createUserRequestWithoutFirstName() {
        return Instancio.of(CreateUserRequest.class)
                .generate(field(CreateUserRequest::email), gen -> gen.text().pattern("#a#a#a#a#a#a@mail.com"))
                .generate(field(CreateUserRequest::firstName), gen -> gen.text().pattern(""))
                .generate(field(CreateUserRequest::role), gen -> gen.oneOf(VALID_ROLES))
                .create();
    }

     public static CreateUserRequest createUserRequestWithoutPassword() {
         return Instancio.of(CreateUserRequest.class)
                 .generate(field(CreateUserRequest::email), gen -> gen.text().pattern("#a#a#a#a#a#a@mail.com"))
                 .generate(field(CreateUserRequest::password), gen -> gen.text().pattern(""))
                 .generate(field(CreateUserRequest::role), gen -> gen.oneOf(VALID_ROLES))
                 .create();
     }

     public static CreateUserRequest createUserRequestWithoutLastName() {
         return Instancio.of(CreateUserRequest.class)
                 .generate(field(CreateUserRequest::email), gen -> gen.text().pattern("#a#a#a#a#a#a@mail.com"))
                    .generate(field(CreateUserRequest::lastName), gen -> gen.text().pattern(""))
                 .generate(field(CreateUserRequest::role), gen -> gen.oneOf(VALID_ROLES))
                 .create();
     }


}
