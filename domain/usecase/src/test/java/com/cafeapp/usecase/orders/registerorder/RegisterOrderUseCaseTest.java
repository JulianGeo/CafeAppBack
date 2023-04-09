package com.cafeapp.usecase.orders.registerorder;

import com.cafeapp.model.order.Order;
import com.cafeapp.model.order.gateways.OrderRepositoryGateway;
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
class RegisterOrderUseCaseTest {

    @Mock
    OrderRepositoryGateway orderRepositoryGateway;

    RegisterOrderUseCase registerOrderUseCase;

    @BeforeEach
    void setUp() {
        registerOrderUseCase = new RegisterOrderUseCase(orderRepositoryGateway);
    }

    @Test
    @DisplayName("registerOrder_Success")
    void register(){
        var monoOrder = Mono.just(InstanceProvider.order());
        var order = InstanceProvider.order();

        Mockito.when(orderRepositoryGateway.registerOrder(ArgumentMatchers.any(Order.class))).thenReturn(monoOrder);

        var service = registerOrderUseCase.apply(order);

        StepVerifier.create(service)
                .expectNextMatches(
                        order1 -> order1.getId().equals(
                                InstanceProvider
                                        .order()
                                        .getId()))
                .verifyComplete();
        Mockito.verify(orderRepositoryGateway).registerOrder(ArgumentMatchers.any(Order.class));
    }

}