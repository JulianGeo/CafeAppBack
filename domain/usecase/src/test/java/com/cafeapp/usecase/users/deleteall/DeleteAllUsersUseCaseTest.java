package com.cafeapp.usecase.users.deleteall;

import com.cafeapp.model.user.User;
import com.cafeapp.model.user.gateways.UserRepositoryGateway;
import com.cafeapp.model.utils.InstanceProvider;
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
                .expectComplete();
        Mockito.verify(userRepositoryGateway).deleteAll();
    }

}