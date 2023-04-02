package com.cafeapp.config;

import com.cafeapp.model.user.gateways.UserRepositoryGateway;
import com.cafeapp.usecase.getallusers.GetAllUsersUseCase;
import com.cafeapp.usecase.registeruser.RegisterUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

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
}
