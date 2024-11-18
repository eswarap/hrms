package org.woven.hrms;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.woven.hrms.employee.entity.Employee;
import org.woven.hrms.employee.model.Gender;

import java.time.LocalDate;
import java.util.List;
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
	@Order(2)
	void getAllEmployees(){
		given().auth().basic("admin","admin")
				.get("/api/v1/hrms/employees").then().statusCode(200);
	}

	@Test
	@Order(1)
	void getEmployee(){
		given().auth().basic("admin","admin")
				.when().get("/api/v1/hrms/employee/1").then().statusCode(200);
	}

	@Test
	@Order(0)
	void addEmployee() {
		Employee employee = new Employee((long) new Random().nextInt(300),"Tom","Holland", Gender.Male,
				LocalDate.EPOCH,LocalDate.EPOCH,
				"tom.holland@example.com","Chief Of operations");
		given().auth().basic("admin","admin").when().contentType("application/json")
				.body(employee).post("/api/v1/hrms/employee")
				.thenReturn().body().prettyPrint();
	}

	@Test
	@Order(3)
	void deleteEmployee(){

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode employees  = given().auth().basic("admin","admin")
				.when().get("/api/v1/hrms/employees").body().as(JsonNode.class);

		List<Employee> empList = objectMapper.convertValue(employees, new TypeReference<List<Employee>>() {
		});

		Employee employee = (Employee) empList.get(0);
		given().auth().basic("admin", "admin")
				.when().delete("/api/v1/hrms/employee/"+employee.getId()).then().statusCode(200);
	}
}
