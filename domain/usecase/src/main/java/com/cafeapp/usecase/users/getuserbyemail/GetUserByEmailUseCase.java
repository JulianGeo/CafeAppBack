package com.cafeapp.usecase.users.getuserbyemail;

import com.cafeapp.model.user.User;
import com.cafeapp.model.user.gateways.UserRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetUserByEmailUseCase implements Function<String, Mono<User>> {

    //TODO: refactor for get by email
    private final UserRepositoryGateway repositoryGateway;

    @Override
    public Mono<User> apply(String email) {
        return repositoryGateway.getUserById(email);
    }
}
