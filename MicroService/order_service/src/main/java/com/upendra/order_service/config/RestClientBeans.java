package com.upendra.order_service.config;

import com.upendra.order_service.client.InventoryServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.net.URI;

@Configuration
public class RestClientBeans {

    private final RestClient.Builder restClientBuilder;

    public RestClientBeans(RestClient.Builder restClientBuilder) {
        this.restClientBuilder = restClientBuilder;
    }

    @Bean
    public InventoryServiceClient inventoryServiceClient() {
        RestClient restClient = restClientBuilder
                .baseUrl(URI.create("http://inventory-service"))
                .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(InventoryServiceClient.class);
    }
}
