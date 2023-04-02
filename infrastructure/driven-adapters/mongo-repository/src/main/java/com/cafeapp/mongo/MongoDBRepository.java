package com.cafeapp.mongo;

import com.cafeapp.mongo.data.UserData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;

public interface MongoDBRepository extends ReactiveMongoRepository<UserData, String> {
}
