package org.woven.demo.hrms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.woven.demo.hrms.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
