package com.cafeapp.model.user.gateways;

import com.cafeapp.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepositoryGateway {
    Flux<User> getAllUsers();
    Mono<User> getUserById(String id);
    Mono<User> getUserByEmail(String id);
    Mono<User> registerUser(User user);
    Mono<User> updateUser(String id, User user);
    Mono<Void> unregisterUser(String id);
}
