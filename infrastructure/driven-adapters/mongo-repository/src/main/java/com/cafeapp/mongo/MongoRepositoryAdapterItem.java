package com.cafeapp.mongo;

import com.cafeapp.model.item.Item;
import com.cafeapp.model.item.gateways.ItemRepositoryGateway;
import com.cafeapp.model.user.User;
import com.cafeapp.mongo.data.ItemData;
import com.cafeapp.mongo.data.UserData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class MongoRepositoryAdapterItem implements ItemRepositoryGateway {

    private final MongoDBRepositoryItem itemRepository;
    private final ObjectMapper mapper;


    @Override
    public Flux<Item> getAllItems() {
        return this.itemRepository
                .findAll()
                .switchIfEmpty(Mono.error(new Throwable("No items available")))
                .map(item -> mapper.map(item, Item.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<Item> getItemById(String id) {

        return this.itemRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Item not found")))
                .map(item -> mapper.map(item, Item.class));
    }

    @Override
    public Mono<Item> getItemByName(String id) {
        return null;
    }

    @Override
    public Mono<Item> registerItem(Item item) {
        return this.itemRepository
                .save(mapper.map(item, ItemData.class))
                .map(item1 -> mapper.map(item1, Item.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<Item> updateItem(String id, Item newItem) {
        return this.itemRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("No item matches the provided ID")))
                .flatMap(oldItem ->{
                    newItem.setId(oldItem.getId());
                    return itemRepository.save(mapper.map(newItem, ItemData.class));
                }).map(newItem1 -> mapper.map(newItem1, Item.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<Void> unregisterItem(String id) {

        return this.itemRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Item not found")))
                .flatMap(item -> this.itemRepository.deleteById(id))
                //TODO: fix it to catch the error
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<Void> deleteAll() {
        return this.itemRepository.deleteAll()
                .onErrorResume(Mono::error);
    }
}
