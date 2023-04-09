package com.cafeapp.usecase.users.getall;

import com.cafeapp.model.user.gateways.UserRepositoryGateway;
import com.cafeapp.model.utils.InstanceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllUsersUseCaseTest {

    @Mock
    UserRepositoryGateway userRepositoryGateway;

    GetAllUsersUseCase getAllUsersUseCase;

    @BeforeEach
    void setUp() {
        getAllUsersUseCase = new GetAllUsersUseCase(userRepositoryGateway);
    }

    @Test
    @DisplayName("getAllUsers_Success")
    void get(){
        var fluxUsers =Flux.just(
                InstanceProvider.oldUser(),
                InstanceProvider.user()
        );

        when(userRepositoryGateway.getAllUsers()).thenReturn(fluxUsers);

        var service = getAllUsersUseCase.get();

        StepVerifier.create(service)
                .expectNextMatches(user -> user.getEmail().equals("User0@email.com"))
                .expectNextCount(1)
                .verifyComplete();

        verify(userRepositoryGateway).getAllUsers();
    }

}