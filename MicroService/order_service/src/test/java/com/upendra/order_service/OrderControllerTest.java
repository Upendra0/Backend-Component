package com.upendra.order_service;

import io.restassured.RestAssured;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {

    @ServiceConnection
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withUsername("root")
            .withPassword("root")
            .withEnv("MYSQL_ROOT_PASSWORD","root");

    @DynamicPropertySource
    static void configureTestProperties(DynamicPropertyRegistry registry){
        System.out.printf("JDBC URL: %s%n",mysql.getJdbcUrl());
        registry.add("spring.datasource.url",() -> mysql.getJdbcUrl());
        registry.add("spring.datasource.username",() -> mysql.getUsername());
        registry.add("spring.datasource.password",() -> mysql.getPassword());
        registry.add("spring.jpa.hibernate.ddl-auto",() -> "none");

    }

    @LocalServerPort
    private int port;

    @BeforeAll
    static void start(){
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
        RestAssured.given()
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
