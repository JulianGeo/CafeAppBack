package com.cafeapp.usecase.items.getitembyid;

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
class GetItemByIdUseCaseTest {

    @Mock
    ItemRepositoryGateway itemRepositoryGateway;
    GetItemByIdUseCase getItemByIdUseCase;

    @BeforeEach
    void setUp() {
        getItemByIdUseCase = new GetItemByIdUseCase(itemRepositoryGateway);
    }


    @Test
    @DisplayName("GetOItemByIdUseCase()")
    void get() {

        Item item = InstanceProvider.item();

        Mockito.when(itemRepositoryGateway.getItemById(ArgumentMatchers.anyString()))
                .thenReturn(Mono.just(item));

        var service = getItemByIdUseCase.apply("ItemID");

        StepVerifier.create(service)
                //.expectNextCount(1)
                .expectNextMatches(item1 -> item1.getStock() == 10)
                .verifyComplete();
        Mockito.verify(itemRepositoryGateway).getItemById("ItemID");
    }
}