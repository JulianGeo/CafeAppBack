package com.cafeapp.usecase.users.updateuser;

import com.cafeapp.model.item.Item;
import com.cafeapp.model.user.User;
import com.cafeapp.model.user.gateways.UserRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class UpdateUserUseCase implements BiFunction<String, User, Mono<User>> {

    private final UserRepositoryGateway repositoryGateway;

    @Override
    public Mono<User> apply(String id, User user) {
        return repositoryGateway.updateUser(id, user);
    }
}
