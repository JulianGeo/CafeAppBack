package com.cafeapp.usecase.users.updateuser;

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

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseTest {

    @Mock
    UserRepositoryGateway userRepositoryGateway;

    UpdateUserUseCase updateUserUseCase;

    @BeforeEach
    void setUp() {
        updateUserUseCase = new UpdateUserUseCase(userRepositoryGateway);
    }

    @Test
    @DisplayName("updateUser_Success")
    void updateUser(){
        var userID = "ID1";
        var newUser = InstanceProvider.user();
        var monoNewUser = Mono.just(InstanceProvider.user());

        Mockito.when(userRepositoryGateway.
                updateUser(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(User.class))
                )
                .thenReturn(monoNewUser);

        var service = updateUserUseCase.apply(userID,
                newUser);

        StepVerifier.create(service)
                .expectNextMatches(
                        user1 -> user1.getLastname().equals(
                                InstanceProvider
                                        .user()
                                        .getLastname()))
                .verifyComplete();
        Mockito.verify(userRepositoryGateway).
                updateUser(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(User.class)
                );
    }

}