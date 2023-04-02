package com.cafeapp.config;

import com.cafeapp.model.item.gateways.ItemRepositoryGateway;
import com.cafeapp.model.user.gateways.UserRepositoryGateway;
import com.cafeapp.usecase.items.getallitems.GetAllItemsUseCase;
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

        @Bean
        public GetAllUsersUseCase getAllStudentsUseCase(UserRepositoryGateway gateway){
                return new GetAllUsersUseCase(gateway);
        }

        @Bean
        public RegisterUserUseCase registerUserUseCase(UserRepositoryGateway gateway){
                return new RegisterUserUseCase(gateway);
        }

        @Bean
        public GetAllItemsUseCase getAllItemsUseCase(ItemRepositoryGateway gateway){
                return new GetAllItemsUseCase(gateway);
        }

        @Bean
        public RegisterItemUseCase registerItemUseCase(ItemRepositoryGateway gateway){
                return new RegisterItemUseCase(gateway);
        }
}
