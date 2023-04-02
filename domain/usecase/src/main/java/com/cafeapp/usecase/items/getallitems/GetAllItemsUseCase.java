package com.cafeapp.usecase.items.getallitems;

import com.cafeapp.model.item.Item;
import com.cafeapp.model.item.gateways.ItemRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class GetAllItemsUseCase implements Supplier<Flux<Item>> {

    private final ItemRepositoryGateway repositoryGateway;

    @Override
    public Flux<Item> get() {
        return repositoryGateway.getAllItems();
    }

}
