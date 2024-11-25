package org.woven.hrms.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.woven.hrms.employee.entity.Role;

public interface RoleRepository extends JpaRepository<Role,String> {
    Role findByRoleName(String roleName);
}

