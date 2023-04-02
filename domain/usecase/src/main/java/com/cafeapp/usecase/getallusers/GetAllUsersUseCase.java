package com.cafeapp.usecase.getallusers;

import com.cafeapp.model.user.User;
import com.cafeapp.model.user.gateways.UserRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class GetAllUsersUseCase implements Supplier<Flux<User>> {

    private final UserRepositoryGateway repositoryGateway;

    @Override
    public Flux<User> get() {
        return repositoryGateway.getAllUsers();
    }
}
