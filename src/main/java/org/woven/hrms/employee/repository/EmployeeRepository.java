package org.woven.hrms.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.woven.hrms.employee.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
