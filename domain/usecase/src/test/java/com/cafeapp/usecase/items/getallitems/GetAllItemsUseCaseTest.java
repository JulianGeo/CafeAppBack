package com.cafeapp.usecase.items.getallitems;

import com.cafeapp.model.item.gateways.ItemRepositoryGateway;
import com.cafeapp.model.utils.InstanceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllItemsUseCaseTest {

    @Mock
    ItemRepositoryGateway itemRepositoryGateway;

    GetAllItemsUseCase getAllItemsUseCase;

    @BeforeEach
    void setUp() {
        getAllItemsUseCase = new GetAllItemsUseCase(itemRepositoryGateway);
    }

    @Test
    @DisplayName("getAllItems_Success")
    void get(){
        var fluxItems =Flux.just(
                InstanceProvider.oldItem(),
                InstanceProvider.item()
        );

        when(itemRepositoryGateway.getAllItems()).thenReturn(fluxItems);

        var service = getAllItemsUseCase.get();

        StepVerifier.create(service)
                .expectNextMatches(item -> item.getStock()==15)
                .expectNextCount(1)
                .verifyComplete();

        verify(itemRepositoryGateway).getAllItems();
    }
}