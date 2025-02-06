package com.upendra.api_gateway.routes;

import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.filter.RetryFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;
import java.util.Set;

@Configuration
public class Routes {

    private final String fallbackRoute = "/fallback";

    @Bean
    public RouterFunction<ServerResponse> productServiceRoute(){
        return GatewayRouterFunctions.route("product-service")
                .route(GatewayRequestPredicates.path("/api/product/**"), HandlerFunctions.http("http://localhost:8080"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("product-service-circuit-breaker", URI.create("forward:" + fallbackRoute)))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceRoute(){
        return GatewayRouterFunctions.route("inventory-service")
                .route(GatewayRequestPredicates.path("/api/inventory/**"), HandlerFunctions.http("http://localhost:8081"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("inventory-service-circuit-breaker", URI.create("forward:" + fallbackRoute)))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceRote(){
        return GatewayRouterFunctions.route("order-service")
                .route(GatewayRequestPredicates.path("/api/order/**"), HandlerFunctions.http("http://localhost:8082"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("order-service-circuit-breaker", URI.create("forward:" + fallbackRoute)))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> fallbackRoute(){
        return GatewayRouterFunctions.route("fallback")
                .route(GatewayRequestPredicates.path(fallbackRoute), (request -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Service is down")))
                .build();
    }

}
