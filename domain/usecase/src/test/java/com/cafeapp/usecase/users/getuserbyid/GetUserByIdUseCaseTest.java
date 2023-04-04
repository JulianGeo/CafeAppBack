package com.cafeapp.usecase.users.getuserbyid;

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

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class GetUserByIdUseCaseTest {

    @Mock
    UserRepositoryGateway userRepositoryGateway;

    GetUserByIdUseCase getUserByIdUseCase;

    @BeforeEach
    void init() {
        getUserByIdUseCase = new GetUserByIdUseCase(userRepositoryGateway);
    }

    @Test
    @DisplayName("GetUserByIdUseCase()")
    void get() {

        User user = new User("UserId", "UserIdNumber", "UserName", "UserLastName", "User@email.com", "Password" );

        Mockito.when(userRepositoryGateway.getUserById(ArgumentMatchers.anyString()))
                .thenReturn(Mono.just(user));

        var service = getUserByIdUseCase.apply("UserID");

        StepVerifier.create(service)
                //.expectNextCount(1)
                .expectNextMatches(user1 -> user1.getEmail().equals("User@email.com"))
                .verifyComplete();
        Mockito.verify(userRepositoryGateway).getUserById("UserID");
    }




}