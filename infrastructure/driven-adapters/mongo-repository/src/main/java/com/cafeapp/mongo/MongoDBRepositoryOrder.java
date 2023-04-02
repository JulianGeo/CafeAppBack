package com.cafeapp.mongo;

import com.cafeapp.mongo.data.OrderData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MongoDBRepositoryOrder extends ReactiveMongoRepository<OrderData, String> {
}
