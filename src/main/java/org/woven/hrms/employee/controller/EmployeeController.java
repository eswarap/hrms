package org.woven.hrms.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.woven.hrms.employee.entity.Employee;
import org.woven.hrms.employee.service.EmployeeService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/hrms")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<org.woven.hrms.employee.entity.Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        Optional<org.woven.hrms.employee.entity.Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employeeDTO) {
        org.woven.hrms.employee.entity.Employee employee = new org.woven.hrms.employee.entity.Employee();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setGender(employeeDTO.getGender());
        employee.setBirthDate(employeeDTO.getBirthDate());
        org.woven.hrms.employee.entity.Employee savedEmployee = employeeService.addEmployee(employee);
        return ResponseEntity.created(URI.create("/api/employees/" + savedEmployee.getId())).body(savedEmployee);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String id,
                                                                                  @RequestBody Employee employeeDTO) {
        org.woven.hrms.employee.entity.Employee employee = new org.woven.hrms.employee.entity.Employee();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setGender(employeeDTO.getGender());
        employee.setBirthDate(employeeDTO.getBirthDate());
        Optional<org.woven.hrms.employee.entity.Employee> existingEmployee = employeeService.updateEmployee(id,
                employee);
        return existingEmployee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
