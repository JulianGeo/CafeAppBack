package com.cafeapp.usecase.orders.getorderbyuserid;

import com.cafeapp.model.order.gateways.OrderRepositoryGateway;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class GetOrderByUserIdUseCase {

    private final OrderRepositoryGateway repositoryGateway;
}
