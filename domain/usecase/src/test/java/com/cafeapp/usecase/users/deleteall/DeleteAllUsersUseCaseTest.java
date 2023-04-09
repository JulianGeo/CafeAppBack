package com.cafeapp.usecase.users.deleteall;

import com.cafeapp.model.user.gateways.UserRepositoryGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class DeleteAllUsersUseCaseTest {

    @Mock
    UserRepositoryGateway userRepositoryGateway;

    DeleteAllUsersUseCase deleteAllUsersUseCase;

    @BeforeEach
    void setUp() {
        deleteAllUsersUseCase = new DeleteAllUsersUseCase(userRepositoryGateway);
    }

    @Test
    @DisplayName("deleteUsers_Success")
    void deleteUser(){

        var monoVoid = Mono.empty().then();

        Mockito.when(userRepositoryGateway.deleteAll()).thenReturn(monoVoid);

        var service = deleteAllUsersUseCase.get();

        StepVerifier.create(service)
                .expectComplete()
                .verify();
        Mockito.verify(userRepositoryGateway).deleteAll();
    }

}