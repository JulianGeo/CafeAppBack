package com.cafeapp.usecase.items.getitembyname;

import com.cafeapp.model.item.Item;
import com.cafeapp.model.item.gateways.ItemRepositoryGateway;
import com.cafeapp.model.user.User;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetItemByNameUseCaseTest {

    @Mock
    ItemRepositoryGateway itemRepositoryGateway;

    GetItemByNameUseCase getItemByNameUseCase;

    @BeforeEach
    void setUp() {
        getItemByNameUseCase = new GetItemByNameUseCase(itemRepositoryGateway);
    }

    @Test
    @DisplayName("GetItemByNameUseCase")
    void get() {

        Item item =InstanceProvider.item();

        Mockito.when(itemRepositoryGateway.getItemByName(ArgumentMatchers.anyString()))
                .thenReturn(Mono.just(item));

        var service = getItemByNameUseCase.apply("ItemName");

        StepVerifier.create(service)
                //.expectNextCount(1)
                .expectNextMatches(item1 -> item1.getPrice()==5)
                .verifyComplete();
        Mockito.verify(itemRepositoryGateway).getItemByName("ItemName");
    }

}