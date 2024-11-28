package org.woven.employee.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woven.employee.employee.entity.Employee;
import org.woven.employee.employee.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(final Long id) {
        return employeeRepository.findById(id);
    }

    public Employee addEmployee(final Employee employee) {
        return employeeRepository.save(employee);
    }

    public Optional<Employee> updateEmployee(final Long id, final Employee employee) {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isPresent()) {
            Employee updatedEmployee = existingEmployee.get();
            updatedEmployee.setFirstName(employee.getFirstName());
            updatedEmployee.setLastName(employee.getLastName());
            updatedEmployee.setGender(employee.getGender());
            updatedEmployee.setBirthDate(employee.getBirthDate());
            updatedEmployee.setJoiningDate(employee.getJoiningDate());
            updatedEmployee.setEmail(employee.getEmail());
            updatedEmployee.setPosition(employee.getPosition());
            employeeRepository.save(updatedEmployee);
        }
        return existingEmployee;
    }

    public void deleteEmployee(final Long id) {
        employeeRepository.deleteById(id);
    }
}
