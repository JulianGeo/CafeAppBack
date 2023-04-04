package com.cafeapp.api;

import com.cafeapp.model.item.Item;
import com.cafeapp.model.order.Order;
import com.cafeapp.model.user.User;
import com.cafeapp.usecase.items.getitembyid.GetItemByIdUseCase;
import com.cafeapp.usecase.items.registeritem.RegisterItemUseCase;
import com.cafeapp.usecase.orders.deleteall.DeleteAllOrdersUseCase;
import com.cafeapp.usecase.orders.getallorders.GetAllOrdersUseCase;
import com.cafeapp.usecase.orders.getorderbyid.GetOrderByIdUseCase;
import com.cafeapp.usecase.orders.registerorder.RegisterOrderUseCase;
import com.cafeapp.usecase.orders.unregisterorder.UnregisterOrderUseCase;
import com.cafeapp.usecase.orders.updateorder.UpdateOrderUseCase;
import com.cafeapp.usecase.users.unregisteruser.UnregisterUserUseCase;
import com.cafeapp.usecase.users.updateuser.UpdateUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRestOrder {


    @Bean
    @RouterOperation(path = "/api/orders", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetAllOrdersUseCase.class, method = RequestMethod.GET,
            beanMethod = "get",
            operation = @Operation(operationId = "getAllOrders", tags = "Order usecases",
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content(schema = @Schema(implementation = Order.class))),
                            @ApiResponse(responseCode = "204", description = "No orders found")
                    }))
    public RouterFunction<ServerResponse> getAllOrders(GetAllOrdersUseCase getAllOrdersUseCase){
        return route(GET("/api/orders"),
                request -> ServerResponse.status(200)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllOrdersUseCase.get(), Order.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
    }


    @Bean
    @RouterOperation(path = "/api/orders/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetOrderByIdUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getOrderById", tags = "Order usecases",
                    parameters = {@Parameter(name = "id", description = "order Id", required = true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content(schema = @Schema(implementation = Item.class))),
                            @ApiResponse(responseCode = "404", description = "Not Found")
                    }))
    public RouterFunction<ServerResponse> getOrderById(GetOrderByIdUseCase getOrderByIdUseCasel){
        return route(GET("/api/orders/{id}"),
                request -> getOrderByIdUseCasel.apply(request.pathVariable("id"))
                        .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                        .flatMap(order -> ServerResponse.status(200)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(order))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }






    @Bean
    @RouterOperation(path = "/api/orders", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = RegisterOrderUseCase.class, method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(operationId = "registerOrder", tags = "Order usecases",
                    parameters ={@Parameter(
                            name = "order",
                            in = ParameterIn.PATH,
                            schema =@Schema(implementation = Order.class))
                    },
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content(schema = @Schema(implementation = Order.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable, Try again")
                    },
                    requestBody = @RequestBody(
                            required=true,
                            description= "Register order",
                            content = @Content(schema = @Schema(implementation = Order.class)))))
    public RouterFunction<ServerResponse> registerOrder(RegisterOrderUseCase registerOrderUseCase) {
        return route(POST("/api/orders").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Order.class)
                        .flatMap(student -> registerOrderUseCase.apply(student)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }


    @Bean
    @RouterOperation(path = "/api/orders/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = UpdateUserUseCase.class, method = RequestMethod.PUT,
            beanMethod = "apply",
            operation = @Operation(operationId = "updateOrder", tags = "Order usecases",
                    parameters = {@Parameter(
                            name = "id",
                            description = "Order Id",
                            required = true,
                            in = ParameterIn.PATH),
                            @Parameter(
                                    name = "order",
                                    in = ParameterIn.PATH,
                                    schema =@Schema(implementation = Order.class)),
                            @Parameter(
                                    name = "user",
                                    in = ParameterIn.PATH,
                                    schema =@Schema(implementation = User.class))},
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content(schema = @Schema(implementation = Order.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable, Try again")
                    },
                    requestBody = @RequestBody(
                            required=true,
                            description= "Register order",
                            content = @Content(schema = @Schema(implementation = Order.class)))))
    public RouterFunction<ServerResponse> updateOrder(UpdateOrderUseCase updateOrderUseCase) {
        return route(PUT("/api/orders/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Order.class)
                        .flatMap(order -> updateOrderUseCase.apply(request.pathVariable("id"), order)
                                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }


    @Bean
    @RouterOperation(path = "/api/orders/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = UnregisterOrderUseCase.class, method = RequestMethod.DELETE,
            beanMethod = "apply",
            operation = @Operation(operationId = "unregisterOrderId", tags = "Order usecases",
                    parameters = {@Parameter(name = "id", description = "Order Id", required = true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content(schema = @Schema(implementation = String.class))),
                            //TODO: fix the code error
                            @ApiResponse(responseCode = "204", description = "No content")
                    }))
    public RouterFunction<ServerResponse> unregisterOrderId(UnregisterOrderUseCase unregisterOrderUseCase) {
        return route(DELETE("api/orders/{id}"),
                request -> unregisterOrderUseCase.apply(request.pathVariable("id"))
                        .thenReturn(ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("Order with ID: "+request.pathVariable("id") +", was unregistered"))
                        .flatMap(serverResponseMono -> serverResponseMono)
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
    }

    @Bean
    @RouterOperation(path = "/api/orders", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = DeleteAllOrdersUseCase.class, method = RequestMethod.DELETE,
            beanMethod = "get",
            operation = @Operation(operationId = "deleteAllOrders", tags = "Order usecases",
                    parameters ={@Parameter(
                            name = "order",
                            in = ParameterIn.PATH,
                            schema =@Schema(implementation = Order.class))
                    },
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content(schema = @Schema(implementation = Order.class))),
                            @ApiResponse(responseCode = "204", description = "No content")
                    }))
    public RouterFunction<ServerResponse> deleteAllOrders(DeleteAllOrdersUseCase deleteAllOrdersUseCase){
        return route(DELETE("api/orders"),
                request -> deleteAllOrdersUseCase.get()
                        .thenReturn(
                        ServerResponse.status(201)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue("All orders have been deleted"))
                        .flatMap(serverResponseMono -> serverResponseMono)
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
    }
}
