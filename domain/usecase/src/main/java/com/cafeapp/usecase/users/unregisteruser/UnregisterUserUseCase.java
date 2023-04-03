package com.cafeapp.usecase.users.unregisteruser;

import com.cafeapp.model.user.gateways.UserRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class UnregisterUserUseCase implements Function<String, Mono<Void>> {

    private final UserRepositoryGateway repositoryGateway;

    @Override
    public Mono<Void> apply(String email) {
        return repositoryGateway.unregisterUser(email);
    }
}
