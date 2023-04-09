package com.cafeapp.usecase.users.unregisteruser;

import com.cafeapp.model.user.gateways.UserRepositoryGateway;
import com.cafeapp.model.utils.InstanceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UnregisterUserUseCaseTest {

    @Mock
    UserRepositoryGateway userRepositoryGateway;

    UnregisterUserUseCase unregisterUserUseCase;

    @BeforeEach
    void setUp() {
        unregisterUserUseCase = new UnregisterUserUseCase(userRepositoryGateway);
    }

    @Test
    @DisplayName("unregisterUserByID_Success")
    void deleteUser(){
        var userID = "ID1";

        Mockito.when(userRepositoryGateway.unregisterUser(userID)).thenReturn(Mono.empty());

        var service = unregisterUserUseCase.apply(userID);

        StepVerifier.create(service)
                .expectNextCount(0)
                .verifyComplete();
        Mockito.verify(userRepositoryGateway).unregisterUser(userID);
    }


}