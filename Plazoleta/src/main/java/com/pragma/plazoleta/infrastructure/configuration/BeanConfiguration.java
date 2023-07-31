package com.pragma.plazoleta.infrastructure.configuration;

import com.pragma.plazoleta.domain.api.*;
import com.pragma.plazoleta.domain.spi.*;
import com.pragma.plazoleta.domain.usecase.*;
import com.pragma.plazoleta.infrastructure.out.jpa.adapter.*;
import com.pragma.plazoleta.infrastructure.out.jpa.mapper.*;
import com.pragma.plazoleta.infrastructure.out.jpa.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.logging.Logger;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    private final IEmployeeRepository employeeRepository;
    private final IEmployeeEntityMapper employeeEntityMapper;
    private final IUserRepository userRepository;
    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IOrderDishRepository orderDishRepository;
    private final IOrderDishEntityMapper orderDishEntityMapper;
    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantJpaAdapter(restaurantRepository, restaurantEntityMapper);
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort());
    }

    @Bean
    public IDishPersistencePort dishPersistencePort() {
        return new DishJpaAdapter(dishRepository, dishEntityMapper);
    }

    @Bean
    public IDishServicePort dishServicePort() {
        return new DishUseCase(dishPersistencePort());
    }

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public IEmployeePersistencePort employeePersistencePort() {
        return new EmployeeJpaAdapter(employeeRepository, employeeEntityMapper);
    }

    @Bean
    public IEmployeeServicePort employeeServicePort() {
        return new EmployeeUseCase(employeePersistencePort());
    }

    @Bean
    public IOrderServicePort orderServicePort () {
        return new OrderUseCase(orderPersistencePort());
    }

    @Bean
    public IOrderPersistencePort orderPersistencePort() {
        return new OrderJpaAdapter(orderRepository, orderEntityMapper);
    }

    @Bean
    public IOrderDishServicePort orderDishServicePort () {
        return new OrderDishUseCase(orderDishPersistencePort());
    }

    @Bean
    public IOrderDishPersistencePort orderDishPersistencePort() {
        return new OrderDishJpaAdapter(orderDishRepository, orderDishEntityMapper);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> (UserDetails) userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public Logger logger() {
        return Logger.getLogger("myLogger"); // "myLogger" is the name of your logger, you can choose any name you prefer
    }
}