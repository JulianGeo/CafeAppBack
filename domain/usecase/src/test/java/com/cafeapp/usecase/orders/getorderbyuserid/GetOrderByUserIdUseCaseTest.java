package com.cafeapp.usecase.orders.getorderbyuserid;

import com.cafeapp.model.order.gateways.OrderRepositoryGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class GetOrderByUserIdUseCaseTest {

    @Mock
    OrderRepositoryGateway orderRepositoryGateway;
    GetOrderByUserIdUseCase getOrderByUserIdUseCase;

    //TODO: still to implement
    @BeforeEach
    void setUp() {
        getOrderByUserIdUseCase = new GetOrderByUserIdUseCase(orderRepositoryGateway);
    }


}