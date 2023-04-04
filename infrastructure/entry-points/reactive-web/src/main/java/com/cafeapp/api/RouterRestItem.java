package com.cafeapp.api;

import com.cafeapp.model.item.Item;
import com.cafeapp.model.order.Order;
import com.cafeapp.model.user.User;
import com.cafeapp.usecase.items.deleteall.DeleteAllItemsUseCase;
import com.cafeapp.usecase.items.getallitems.GetAllItemsUseCase;
import com.cafeapp.usecase.items.getitembyid.GetItemByIdUseCase;
import com.cafeapp.usecase.items.getitembyname.GetItemByNameUseCase;
import com.cafeapp.usecase.items.registeritem.RegisterItemUseCase;
import com.cafeapp.usecase.items.unregisteritem.UnregisterItemUseCase;
import com.cafeapp.usecase.items.updateitem.UpdateItemUseCase;
import com.cafeapp.usecase.orders.getallorders.GetAllOrdersUseCase;
import com.cafeapp.usecase.users.deleteall.DeleteAllUsersUseCase;
import com.cafeapp.usecase.users.getuserbyemail.GetUserByEmailUseCase;
import com.cafeapp.usecase.users.getuserbyid.GetUserByIdUseCase;
import com.cafeapp.usecase.users.register.RegisterUserUseCase;
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
public class RouterRestItem {



    @Bean
    @RouterOperation(path = "/api/items", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetAllOrdersUseCase.class, method = RequestMethod.GET,
            beanMethod = "get",
            operation = @Operation(operationId = "getAllItems", tags = "Item usecases",
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content(schema = @Schema(implementation = Item.class))),
                            @ApiResponse(responseCode = "204", description = "No items found")
                    }))
    public RouterFunction<ServerResponse> getAllItems(GetAllItemsUseCase getAllItemsUseCase){
        return route(GET("/api/items"),
                request -> ServerResponse.status(200)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllItemsUseCase.get(), Item.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
    }

    @Bean
    @RouterOperation(path = "/api/items/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetItemByIdUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getItemsById", tags = "Item usecases",
                    parameters = {@Parameter(name = "id", description = "item Id", required = true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content(schema = @Schema(implementation = Item.class))),
                            @ApiResponse(responseCode = "404", description = "Item not Found")
                    }))
    public RouterFunction<ServerResponse> getItemsById(GetItemByIdUseCase getItemByIdUseCase){
        return route(GET("/api/items/{id}"),
                request -> getItemByIdUseCase.apply(request.pathVariable("id"))
                        .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                        .flatMap(item -> ServerResponse.status(200)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(item))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }

    //If there is more than an item with the given name it will return 404 not found

    @Bean
    @RouterOperation(path = "/api/items/name/{name}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetItemByNameUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getItemByName", tags = "Item usecases",
                    parameters = {@Parameter(name = "name", description = "item name", required = true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content(schema = @Schema(implementation = Item.class))),
                            @ApiResponse(responseCode = "404", description = "Item not Found")
                    }))
    public RouterFunction<ServerResponse> getItemByName(GetItemByNameUseCase getItemByNameUseCase){
        return route(GET("/api/items/name/{name}"),
                request -> getItemByNameUseCase.apply(request.pathVariable("name"))
                        .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                        .flatMap(item -> ServerResponse.status(200)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(item))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }


    @Bean
    @RouterOperation(path = "/api/items", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = RegisterItemUseCase.class, method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(operationId = "registerItem", tags = "Item usecases",
                    parameters ={@Parameter(
                            name = "item",
                            in = ParameterIn.PATH,
                            schema =@Schema(implementation = Item.class))
                    },
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content(schema = @Schema(implementation = Item.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable, Try again")
                    },
                    requestBody = @RequestBody(
                            required=true,
                            description= "Register item",
                            content = @Content(schema = @Schema(implementation = Item.class)))))
    public RouterFunction<ServerResponse> registerItem(RegisterItemUseCase registerItemUseCase) {
        return route(POST("/api/items").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Item.class)
                        .flatMap(item -> registerItemUseCase.apply(item)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }


    @Bean
    @RouterOperation(path = "/api/items/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = UpdateItemUseCase.class, method = RequestMethod.PUT,
            beanMethod = "apply",
            operation = @Operation(operationId = "updateItem", tags = "Item usecases",
                    parameters = {@Parameter(
                            name = "id",
                            description = "Item Id",
                            required = true,
                            in = ParameterIn.PATH),

                            @Parameter(
                                    name = "item",
                                    in = ParameterIn.PATH,
                                    schema =@Schema(implementation = Item.class))},
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content(schema = @Schema(implementation = Item.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable, Try again")
                    },
                    requestBody = @RequestBody(
                            required=true,
                            description= "Register item",
                            content = @Content(schema = @Schema(implementation = Item.class)))))
    public RouterFunction<ServerResponse> updateItem(UpdateItemUseCase updateItemUseCase) {
        return route(PUT("/api/items/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Item.class)
                        .flatMap(item -> updateItemUseCase.apply(request.pathVariable("id"), item)
                                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }



    @Bean
    @RouterOperation(path = "/api/items/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = UnregisterItemUseCase.class, method = RequestMethod.DELETE,
            beanMethod = "apply",
            operation = @Operation(operationId = "unregisterItemById", tags = "Item usecases",
                    parameters = {@Parameter(name = "id", description = "Item Id", required = true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content(schema = @Schema(implementation = Item.class))),
                            @ApiResponse(responseCode = "404", description = "No content")
                    }))
    public RouterFunction<ServerResponse> unregisterItemById(UnregisterItemUseCase unregisterItemUseCase){
        return route(DELETE("api/items/{id}"),
                request -> unregisterItemUseCase.apply(request.pathVariable("id"))
                        .thenReturn(ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("Item with ID: "+request.pathVariable("id") +", was unregistered"))
                        .flatMap(serverResponseMono -> serverResponseMono)
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
    }


    @Bean
    @RouterOperation(path = "/api/items", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = DeleteAllItemsUseCase.class, method = RequestMethod.DELETE,
            beanMethod = "get",
            operation = @Operation(operationId = "deleteAllItems", tags = "Item usecases",
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content(schema = @Schema(implementation = Item.class))),
                            @ApiResponse(responseCode = "204", description = "No items found")
                    }))
    public RouterFunction<ServerResponse> deleteAllItems(DeleteAllItemsUseCase deleteAllItemsUseCase){
        return route(DELETE("api/items"),
                request -> deleteAllItemsUseCase.get()
                        .thenReturn(
                                ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue("All items have been deleted"))
                        .flatMap(serverResponseMono -> serverResponseMono)
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
    }
}
