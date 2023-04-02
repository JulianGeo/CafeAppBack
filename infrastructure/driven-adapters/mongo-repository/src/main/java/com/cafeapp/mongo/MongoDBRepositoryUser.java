package com.cafeapp.mongo;

import com.cafeapp.mongo.data.UserData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MongoDBRepositoryUser extends ReactiveMongoRepository<UserData, String> {
}
