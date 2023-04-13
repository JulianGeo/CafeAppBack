package com.cafeapp.usecase.orders.cancelorder;

import com.cafeapp.model.order.Order;
import com.cafeapp.model.order.gateways.OrderRepositoryGateway;
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
class CancelOrderUseCaseTest {

    @Mock
    OrderRepositoryGateway orderRepositoryGateway;

    CancelOrderUseCase cancelOrderUseCase;

    @BeforeEach
    void setUp() {
        cancelOrderUseCase = new CancelOrderUseCase(orderRepositoryGateway);
    }

    @Test
    @DisplayName("cancelOrder_Success")
    void updateOrder(){
        var orderID = "ID1";
        var newOrder = InstanceProvider.order();
        var monoNewOrder = Mono.just(InstanceProvider.order());

        Mockito.when(orderRepositoryGateway.
                        cancelOrder(
                                ArgumentMatchers.anyString(),
                                ArgumentMatchers.any(Order.class))
                )
                .thenReturn(monoNewOrder);

        var service = cancelOrderUseCase.apply(orderID,
                newOrder);

        StepVerifier.create(service)
                .expectNextMatches(
                        order1 -> order1.getShipping() ==5)
                .verifyComplete();
        Mockito.verify(orderRepositoryGateway).
                cancelOrder(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(Order.class)
                );
    }
}