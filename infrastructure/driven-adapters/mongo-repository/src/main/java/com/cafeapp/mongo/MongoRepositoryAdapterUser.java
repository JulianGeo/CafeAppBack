package com.cafeapp.mongo;

import com.cafeapp.model.item.Item;
import com.cafeapp.model.user.User;
import com.cafeapp.model.user.gateways.UserRepositoryGateway;
import com.cafeapp.mongo.data.ItemData;
import com.cafeapp.mongo.data.UserData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
//public class MongoRepositoryAdapterUser extends AdapterOperations<Object/* change for domain model */, Object/* change for adapter model */, String, MongoDBRepositoryUser>
public class MongoRepositoryAdapterUser implements UserRepositoryGateway
// implements ModelRepository from domain
{

    private final MongoDBRepositoryUser userRepository;
    private final ObjectMapper mapper;


/*    public MongoRepositoryAdapterUser(MongoDBRepositoryUser repository, ObjectMapper mapper) {
        *//**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         *//*
        super(repository, mapper, d -> mapper.map(d, Object.class*//* change for domain model *//*));
    }*/

    @Override
    public Flux<User> getAllUsers() {
        return this.userRepository
                .findAll()
                .switchIfEmpty(Mono.error(new Throwable("No users available")))
                .map(user -> mapper.map(user, User.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<User> getUserById(String id) {
        return this.userRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("User not found")))
                .map(user -> mapper.map(user, User.class));
    }

    @Override
    public Mono<User> getUserByEmail(String id) {
        return null;
    }

    @Override
    public Mono<User> registerUser(User user) {
        return this.userRepository
                .save(mapper.map(user, UserData.class))
                .map(user1 -> mapper.map(user1, User.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<User> updateUser(String id, User newUser) {
        return this.userRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("No user matches the provided ID")))
                .flatMap(oldUser ->{
                    newUser.setId(oldUser.getId());
                    return userRepository.save(mapper.map(newUser, UserData.class));
                }).map(newUser1 -> mapper.map(newUser1, User.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<Void> unregisterUser(String id) {
        return this.userRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("User not found")))
                .flatMap(user -> this.userRepository.deleteById(id))
                //TODO: fix it to catch the error
                .onErrorResume(Mono::error);
    }
}
