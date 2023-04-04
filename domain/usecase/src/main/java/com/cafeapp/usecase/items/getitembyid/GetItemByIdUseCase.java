package com.cafeapp.usecase.items.getitembyid;

import com.cafeapp.model.item.Item;
import com.cafeapp.model.item.gateways.ItemRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetItemByIdUseCase implements Function<String, Mono<Item>> {

    private final ItemRepositoryGateway repositoryGateway;

    @Override
    public Mono<Item> apply(String id) {
        return repositoryGateway.getItemById(id);
    }
}
