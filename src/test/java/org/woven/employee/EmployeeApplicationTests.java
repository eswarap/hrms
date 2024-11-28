package org.woven.employee;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.woven.employee.employee.entity.Employee;
import org.woven.employee.employee.model.Gender;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class EmployeeApplicationTests {

	@LocalServerPort
	private Integer port;
	private String token;
	@BeforeEach
	 void setUp() {
		RestAssured.port = port;
		RestAssured.defaultParser = Parser.JSON;
	}
	@Test
	@Order(2)
	void getAllEmployees(){
		given().auth().oauth2(token)
				.get("/api/v1/hrms/employees").then().statusCode(200);
	}

	@Test
	@Order(1)
	void getEmployee(){
		given().auth().oauth2(token)
				.when().get("/api/v1/hrms/employee/1").then().statusCode(200);
	}

	@Test
	@Order(0)
	void addEmployee() {
		ObjectMapper mapper = JsonMapper.builder()
				.addModule(new JavaTimeModule())
				.build();
		mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

		Employee employee = Employee.builder().id(new Random().nextLong(10L))
				.firstName("Tom").lastName("Holland").gender(Gender.Male)
				.birthDate(LocalDate.EPOCH).joiningDate(LocalDate.EPOCH).email("tom.holland@example.com")
				.position("Chief Of operations").build();

		JsonNode jsonNode = given().auth().oauth2(token).when().contentType(ContentType.JSON)
				.body(employee).post("/api/v1/hrms/employee")
				.body().as(JsonNode.class);

		Employee newEmployee = mapper.convertValue(jsonNode,Employee.class);

		Assertions.assertEquals(employee,newEmployee);
	}

	@Test
	@Order(3)
	void deleteEmployee(){

		ObjectMapper mapper = JsonMapper.builder()
				.addModule(new JavaTimeModule())
				.build();
		JsonNode employees  = given().auth().oauth2(token)
				.when().get("/api/v1/hrms/employees").body().as(JsonNode.class);

		List<Employee> empList = mapper.convertValue(employees, new TypeReference<List<Employee>>() {
		});

		Employee employee = (Employee) empList.get(0);
		given().auth().oauth2(token)
				.when().delete("/api/v1/hrms/employee/"+employee.getId()).then().statusCode(204);
	}
}
