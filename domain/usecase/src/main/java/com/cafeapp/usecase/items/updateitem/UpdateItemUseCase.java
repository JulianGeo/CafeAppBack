package com.cafeapp.usecase.items.updateitem;

import com.cafeapp.model.item.Item;
import com.cafeapp.model.item.gateways.ItemRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class UpdateItemUseCase implements BiFunction<String, Item, Mono<Item>> {

    private final ItemRepositoryGateway repositoryGateway;

    @Override
    public Mono<Item> apply(String id, Item item) {
        return repositoryGateway.updateItem(id, item);
    }
}
