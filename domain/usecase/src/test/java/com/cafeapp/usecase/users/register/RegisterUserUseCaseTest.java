package com.cafeapp.usecase.users.register;

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
class RegisterUserUseCaseTest {

    @Mock
    UserRepositoryGateway userRepositoryGateway;

    RegisterUserUseCase registerUserUseCase;

    @BeforeEach
    void setUp() {
        registerUserUseCase = new RegisterUserUseCase(userRepositoryGateway);
    }


    @Test
    @DisplayName("registerUser_Success")
    void register(){
        var monoUser = Mono.just(InstanceProvider.user());
        var user = InstanceProvider.user();

        Mockito.when(userRepositoryGateway.registerUser(ArgumentMatchers.any(User.class))).thenReturn(monoUser);

        var service = registerUserUseCase.apply(user);

        StepVerifier.create(service)
                .expectNextMatches(
                        user1 -> user1.getLastname().equals(
                                InstanceProvider
                                        .user()
                                        .getLastname()))
                .verifyComplete();
        Mockito.verify(userRepositoryGateway).registerUser(ArgumentMatchers.any(User.class));
    }

}