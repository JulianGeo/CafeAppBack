package com.cafeapp.model.item.gateways;


import com.cafeapp.model.item.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ItemRepositoryGateway {

    Flux<Item> getAllItems();
    Mono<Item> getItemById(String id);
    Mono<Item> getItemByName(String name);
    Mono<Item> registerItem(Item item);
    Mono<Item> updateItem(String id, Item item);
    Mono<Void> unregisterItem(String id);
    Mono<Void> deleteAll();

}

