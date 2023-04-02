package com.cafeapp.usecase.users.register;

import com.cafeapp.model.user.User;
import com.cafeapp.model.user.gateways.UserRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class RegisterUserUseCase implements Function<User, Mono<User>> {

    private final UserRepositoryGateway repositoryGateway;

    @Override
    public Mono<User> apply(User user) {
        return repositoryGateway.registerUser(user);
    }
}
