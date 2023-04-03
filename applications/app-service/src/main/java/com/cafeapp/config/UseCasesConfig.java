package com.cafeapp.config;

import com.cafeapp.model.item.gateways.ItemRepositoryGateway;
import com.cafeapp.model.order.gateways.OrderRepositoryGateway;
import com.cafeapp.model.user.gateways.UserRepositoryGateway;
import com.cafeapp.usecase.items.getallitems.GetAllItemsUseCase;
import com.cafeapp.usecase.items.getitembyid.GetItemByIdUseCase;
import com.cafeapp.usecase.items.updateitem.UpdateItemUseCase;
import com.cafeapp.usecase.orders.getallorders.GetAllOrdersUseCase;
import com.cafeapp.usecase.orders.registerorder.RegisterOrderUseCase;
import com.cafeapp.usecase.users.getall.GetAllUsersUseCase;
import com.cafeapp.usecase.items.registeritem.RegisterItemUseCase;
import com.cafeapp.usecase.users.register.RegisterUserUseCase;
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
        public RegisterUserUseCase registerUserUseCase(UserRepositoryGateway gateway){
                return new RegisterUserUseCase(gateway);
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

        // ====== Orders =================================

        @Bean
        public GetAllOrdersUseCase getAllOrdersUseCase(OrderRepositoryGateway gateway){
                return new GetAllOrdersUseCase(gateway);
        }

        @Bean
        public RegisterOrderUseCase registerOrderUseCase(OrderRepositoryGateway gateway){
                return new RegisterOrderUseCase(gateway);
        }


}
