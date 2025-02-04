package com.upendra.api_gateway.routes;

import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.setPath;

@Configuration
public class OpenAPIRoutes {

    private final String fallbackRoute = "/fallback";

    @Bean
    public RouterFunction<ServerResponse> productServiceAPIDocRoute(){
        return GatewayRouterFunctions.route("product-service-APIDoc")
                .route(GatewayRequestPredicates.path("product/v3/api-docs"), HandlerFunctions.http("http://localhost:8080"))
                .before(setPath("/api-docs"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("product-service-api doc-circuit-breaker", URI.create("forward:" + fallbackRoute)))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceAPIDocRoute(){
        return GatewayRouterFunctions.route("inventory-service-APIDoc")
                .route(GatewayRequestPredicates.path("inventory/v3/api-docs"), HandlerFunctions.http("http://localhost:8081"))
                .before(setPath("/api-docs"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("inventory-service-api doc-circuit-breaker", URI.create("forward:" + fallbackRoute)))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceAPIDocRoute(){
        return GatewayRouterFunctions.route("order-service-APIDoc")
                .route(GatewayRequestPredicates.path("order/v3/api-docs"), HandlerFunctions.http("http://localhost:8082"))
                .before(setPath("/api-docs"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("order-service-api doc-circuit-breaker", URI.create("forward:" + fallbackRoute)))
                .build();
    }
}
