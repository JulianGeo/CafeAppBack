package com.cafeapp.mongo;

import com.cafeapp.mongo.data.ItemData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MongoDBRepositoryItem extends ReactiveMongoRepository<ItemData, String> {

    <Mono>ItemData findByName(String name);

}
