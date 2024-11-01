package org.woven.hrms;


import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.woven.hrms.employee.entity.Employee;
import org.woven.hrms.employee.model.Gender;

import java.time.LocalDate;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class HRMSApplicationTests {

	@LocalServerPort
	private Integer port;

	@BeforeEach
	 void setUp() {
		RestAssured.port = port;
	}
	@Test
	void getAllEmployees(){
		given().get("/api/v1/hrms/employees").then().statusCode(200);

	}

	@Test
	void getEmployee(){
		given().when().get("/api/v1/hrms/employee/1").then().statusCode(200);
	}

	@Test
	void addEmpployee() {
		Employee employee = new Employee((long) new Random().nextInt(),"Tom","Holland", Gender.Male,
				LocalDate.EPOCH,LocalDate.EPOCH,
				"tom.holland@example.com","Chief Of operations");
		given().when().contentType("application/json").body(employee).post("/api/v1/hrms/employee")
				.thenReturn().body().prettyPrint();
	}
}
