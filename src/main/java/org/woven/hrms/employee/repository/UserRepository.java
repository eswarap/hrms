package org.woven.hrms.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.woven.hrms.employee.entity.User;

public interface UserRepository extends JpaRepository<User,String> {
    User findByUsername(String username);
}
