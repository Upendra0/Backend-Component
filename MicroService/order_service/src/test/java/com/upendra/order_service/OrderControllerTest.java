package com.upendra.order_service;

import com.upendra.order_service.dto.OrderResponse;
import io.restassured.RestAssured;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.web.client.RestClient;
import org.testcontainers.containers.MySQLContainer;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class OrderControllerTest {

    @ServiceConnection
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0");

    private Integer port = 8081;

    static {
        mysql.start();
    }

    @BeforeEach
    void setup(){
        RestAssured.baseURI="http://localhost";
        RestAssured.port=port;
    }

    @Test
    public void shouldCreateValidOrder(){
        String requestBody = """
                {
                    "skuCode":"abc",
                    "quantity":12,
                    "price":12
                }
                """;
        RestClient restClient = RestClient.create("http://localhost:8081");
        OrderResponse orderResponse = restClient.post()
                .uri("/api/v1/placeOrder")
                .body(requestBody)
                .retrieve()
                .body(OrderResponse.class);
        System.out.println("OrderResponse: "+orderResponse);
        RestAssured.port =8081;
        System.out.println("RestAssured URI: "+RestAssured.baseURI+":"+RestAssured.port);
        RestAssured.given()
                .port(port)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/placeOrder")
                .then()
                .statusCode(200)
                .body("skuCode",equalTo("abc"))
                .body("id", notNullValue());
    }
}
