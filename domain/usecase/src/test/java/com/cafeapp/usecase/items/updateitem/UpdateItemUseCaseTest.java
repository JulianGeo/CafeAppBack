package com.cafeapp.usecase.items.updateitem;

import com.cafeapp.model.item.Item;
import com.cafeapp.model.item.gateways.ItemRepositoryGateway;
import com.cafeapp.model.utils.InstanceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@ExtendWith(MockitoExtension.class)
class UpdateItemUseCaseTest {

    @Mock
    ItemRepositoryGateway itemRepositoryGateway;

    UpdateItemUseCase updateItemUseCase;

    @BeforeEach
    void setUp() {
        updateItemUseCase = new UpdateItemUseCase(itemRepositoryGateway);
    }

    @Test
    @DisplayName("updateItem_Success")
    void updateUser(){
        var itemID = "ID1";
        var newItem = InstanceProvider.item();
        var monoNewItem = Mono.just(InstanceProvider.item());

        Mockito.when(itemRepositoryGateway.
                        updateItem(
                                ArgumentMatchers.anyString(),
                                ArgumentMatchers.any(Item.class))
                )
                .thenReturn(monoNewItem);

        var service = updateItemUseCase.apply(itemID,
                newItem);

        StepVerifier.create(service)
                .expectNextMatches(
                        item1 -> item1.getName().equals(
                                InstanceProvider
                                        .item()
                                        .getName()))
                .verifyComplete();

        Mockito.verify(itemRepositoryGateway).
                updateItem(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(Item.class)
                );
    }

}