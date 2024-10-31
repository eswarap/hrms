package org.woven.demo.hrms;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class HRMSApplicationTests {

	@Test
	void contextLoads() {

	}

	@Test
	void getAllEmployees(){
		given().get("/api/v1/hrms/employees").then().statusCode(200);

	}

}
