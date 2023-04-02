package com.cafeapp.mongo;

import com.cafeapp.model.user.User;
import com.cafeapp.model.user.gateways.UserRepositoryGateway;
import com.cafeapp.mongo.data.UserData;
import com.cafeapp.mongo.helper.AdapterOperations;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
//public class MongoRepositoryAdapter extends AdapterOperations<Object/* change for domain model */, Object/* change for adapter model */, String, MongoDBRepository>
public class MongoRepositoryAdapter implements UserRepositoryGateway
// implements ModelRepository from domain
{

    private final MongoDBRepository userRepository;
    private final ObjectMapper mapper;


/*    public MongoRepositoryAdapter(MongoDBRepository repository, ObjectMapper mapper) {
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
        return null;
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
    public Mono<User> updateUser(User user) {
        return null;
    }

    @Override
    public Mono<String> unregisterUser(String id) {
        return null;
    }
}
