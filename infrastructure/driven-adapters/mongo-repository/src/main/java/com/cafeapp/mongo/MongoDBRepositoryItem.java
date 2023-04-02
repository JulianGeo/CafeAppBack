package com.cafeapp.mongo;

import com.cafeapp.mongo.data.ItemData;
import com.cafeapp.mongo.data.UserData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MongoDBRepositoryItem extends ReactiveMongoRepository<ItemData, String> {
}
