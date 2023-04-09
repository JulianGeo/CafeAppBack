package com.cafeapp.usecase.items.registeritem;

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
class RegisterItemUseCaseTest {

    @Mock
    ItemRepositoryGateway itemRepositoryGateway;

    RegisterItemUseCase registerItemUseCase;

    @BeforeEach
    void setUp() {
        registerItemUseCase = new RegisterItemUseCase(itemRepositoryGateway);
    }

    @Test
    @DisplayName("registerItem_Success")
    void register(){
        var monoItem = Mono.just(InstanceProvider.item());
        var item = InstanceProvider.item();

        Mockito.when(itemRepositoryGateway.registerItem(ArgumentMatchers.any(Item.class))).thenReturn(monoItem);

        var service = registerItemUseCase.apply(item);

        StepVerifier.create(service)
                .expectNextMatches(
                        item1 -> item1.getName().equals(
                                InstanceProvider
                                        .item()
                                        .getName()))
                .verifyComplete();
        Mockito.verify(itemRepositoryGateway).registerItem(ArgumentMatchers.any(Item.class));
    }

}