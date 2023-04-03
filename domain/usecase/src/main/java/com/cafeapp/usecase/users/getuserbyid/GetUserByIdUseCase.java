package com.cafeapp.usecase.users.getuserbyid;

import com.cafeapp.model.user.User;
import com.cafeapp.model.user.gateways.UserRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetUserByIdUseCase implements Function<String, Mono<User>> {

    private final UserRepositoryGateway repositoryGateway;

    @Override
    public Mono<User> apply(String id) {
        return repositoryGateway.getUserById(id);
    }
}
