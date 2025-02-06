package com.upendra.order_service.config;

import com.upendra.order_service.client.InventoryServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientBeans {

    @Bean
    public InventoryServiceClient inventoryServiceClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl("http://localhost:8081")
                .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(InventoryServiceClient.class);
    }
}
