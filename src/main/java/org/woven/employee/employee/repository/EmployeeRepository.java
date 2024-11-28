package org.woven.employee.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.woven.employee.employee.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
