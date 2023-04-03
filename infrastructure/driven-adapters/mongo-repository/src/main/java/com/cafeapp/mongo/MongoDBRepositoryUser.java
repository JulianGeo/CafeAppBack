package com.cafeapp.mongo;

import com.cafeapp.mongo.data.UserData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface MongoDBRepositoryUser extends ReactiveMongoRepository<UserData, String> {

    Mono<UserData> findByEmail(String email);
}
