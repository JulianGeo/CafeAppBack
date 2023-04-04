package com.cafeapp.usecase.users.deleteall;

import com.cafeapp.model.user.gateways.UserRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class DeleteAllUsersUseCase implements Supplier<Mono<Void>> {

    private final UserRepositoryGateway repositoryGateway;

    @Override
    public Mono<Void> get() {
        return repositoryGateway.deleteAll();
    }
}
