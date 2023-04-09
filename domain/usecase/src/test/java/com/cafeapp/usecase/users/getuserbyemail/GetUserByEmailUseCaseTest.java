package com.cafeapp.usecase.users.getuserbyemail;

import com.cafeapp.model.user.User;
import com.cafeapp.model.user.gateways.UserRepositoryGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@ExtendWith(MockitoExtension.class)
class GetUserByEmailUseCaseTest {

    @Mock
    UserRepositoryGateway userRepositoryGateway;

    GetUserByEmailUseCase getUserByEmailUseCase;

    @BeforeEach
    void setUp() {
        getUserByEmailUseCase = new GetUserByEmailUseCase(userRepositoryGateway);
    }

    @Test
    @DisplayName("GetUserByEmailUseCase")
    void get() {
        User user = new User("UserId", "UserIdNumber", "UserName", "UserLastName", "User@email.com", "Password" );

        Mockito.when(userRepositoryGateway.getUserByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Mono.just(user));

        var service = getUserByEmailUseCase.apply("User@email.com");

        StepVerifier.create(service)
                //.expectNextCount(1)
                .expectNextMatches(user1 -> user1.getEmail().equals("User@email.com"))
                .verifyComplete();
        Mockito.verify(userRepositoryGateway).getUserByEmail("User@email.com");
    }

}