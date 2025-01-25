package com.upendra.product_service;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;

import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:8.0");

	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
		RestAssured.baseURI="http://localhost";
		RestAssured.port=port;
		RestAssured.authentication = RestAssured.basic("admin", "admin");
	}

	@BeforeAll
	static void resourceSetup() {
		mongoDBContainer.start();
	}


	@Test
	void shouldCreateValidProduct() {
		String requestBody = """
				{
				    "name":"Apple",
				    "description":"Fresh Apples from garden",
				    "price":40
				}
				""";
		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/product/v1/create")
				.then()
				.statusCode(201)
				.body("id", notNullValue());
	}

}
