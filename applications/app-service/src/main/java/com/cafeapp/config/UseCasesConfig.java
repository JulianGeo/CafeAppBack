package com.cafeapp.config;

import com.cafeapp.model.item.gateways.ItemRepositoryGateway;
import com.cafeapp.model.order.gateways.OrderRepositoryGateway;
import com.cafeapp.model.user.gateways.UserRepositoryGateway;
import com.cafeapp.usecase.items.deleteall.DeleteAllItemsUseCase;
import com.cafeapp.usecase.items.getallitems.GetAllItemsUseCase;
import com.cafeapp.usecase.items.getitembyid.GetItemByIdUseCase;
import com.cafeapp.usecase.items.unregisteritem.UnregisterItemUseCase;
import com.cafeapp.usecase.items.updateitem.UpdateItemUseCase;
import com.cafeapp.usecase.orders.deleteall.DeleteAllOrdersUseCase;
import com.cafeapp.usecase.orders.getallorders.GetAllOrdersUseCase;
import com.cafeapp.usecase.orders.getorderbyid.GetOrderByIdUseCase;
import com.cafeapp.usecase.orders.registerorder.RegisterOrderUseCase;
import com.cafeapp.usecase.orders.unregisterorder.UnregisterOrderUseCase;
import com.cafeapp.usecase.orders.updateorder.UpdateOrderUseCase;
import com.cafeapp.usecase.users.deleteall.DeleteAllUsersUseCase;
import com.cafeapp.usecase.users.getall.GetAllUsersUseCase;
import com.cafeapp.usecase.items.registeritem.RegisterItemUseCase;
import com.cafeapp.usecase.users.getuserbyemail.GetUserByEmailUseCase;
import com.cafeapp.usecase.users.getuserbyid.GetUserByIdUseCase;
import com.cafeapp.usecase.users.register.RegisterUserUseCase;
import com.cafeapp.usecase.users.unregisteruser.UnregisterUserUseCase;
import com.cafeapp.usecase.users.updateuser.UpdateUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.cafeapp.usecase")/*,
       includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)*/
public class UseCasesConfig {

        // ====== Users =================================
        @Bean
        public GetAllUsersUseCase getAllUsersUseCase(UserRepositoryGateway gateway){
                return new GetAllUsersUseCase(gateway);
        }

        @Bean
        public GetUserByIdUseCase getUserByIdUseCase(UserRepositoryGateway gateway){
                return new GetUserByIdUseCase(gateway);
        }

        @Bean
        public GetUserByEmailUseCase getUserByEmailUseCase(UserRepositoryGateway gateway){
                return new GetUserByEmailUseCase(gateway);
        }


        @Bean
        public RegisterUserUseCase registerUserUseCase(UserRepositoryGateway gateway){
                return new RegisterUserUseCase(gateway);
        }

        @Bean
        public UpdateUserUseCase updateUserUseCase(UserRepositoryGateway gateway){
                return new UpdateUserUseCase(gateway);
        }

        @Bean
        public UnregisterUserUseCase unregisterUserUseCase(UserRepositoryGateway gateway){
                return new UnregisterUserUseCase(gateway);
        }

        @Bean
        public DeleteAllUsersUseCase deleteAllUsersUseCase(UserRepositoryGateway gateway){
                return new DeleteAllUsersUseCase(gateway);
        }


        // ====== Items =================================
        @Bean
        public GetAllItemsUseCase getAllItemsUseCase(ItemRepositoryGateway gateway){
                return new GetAllItemsUseCase(gateway);
        }

        @Bean
        public GetItemByIdUseCase getItemByIdUseCase(ItemRepositoryGateway gateway){
                return new GetItemByIdUseCase(gateway);
        }

        @Bean
        public RegisterItemUseCase registerItemUseCase(ItemRepositoryGateway gateway){
                return new RegisterItemUseCase(gateway);
        }

        @Bean
        public UpdateItemUseCase updateItemUseCase(ItemRepositoryGateway gateway){
                return new UpdateItemUseCase(gateway);
        }

        @Bean
        public UnregisterItemUseCase unregisterItemUseCase(ItemRepositoryGateway gateway){
                return new UnregisterItemUseCase(gateway);
        }

        @Bean
        public DeleteAllItemsUseCase deleteAllItemsUseCase(ItemRepositoryGateway gateway){
                return new DeleteAllItemsUseCase(gateway);
        }

        // ====== Orders =================================

        @Bean
        public GetAllOrdersUseCase getAllOrdersUseCase(OrderRepositoryGateway gateway){
                return new GetAllOrdersUseCase(gateway);
        }

        @Bean
        public GetOrderByIdUseCase getOrderByIdUseCase(OrderRepositoryGateway gateway){
                return new GetOrderByIdUseCase(gateway);
        }


        @Bean
        public RegisterOrderUseCase registerOrderUseCase(OrderRepositoryGateway gateway){
                return new RegisterOrderUseCase(gateway);
        }

        @Bean
        public UpdateOrderUseCase updateOrderUseCase(OrderRepositoryGateway gateway){
                return new UpdateOrderUseCase(gateway);
        }

        @Bean
        public UnregisterOrderUseCase unregisterOrderUseCase(OrderRepositoryGateway gateway){
                return new UnregisterOrderUseCase(gateway);
        }

        @Bean
        public DeleteAllOrdersUseCase deleteAllOrdersUseCase(OrderRepositoryGateway gateway){
                return new DeleteAllOrdersUseCase(gateway);
        }


}
