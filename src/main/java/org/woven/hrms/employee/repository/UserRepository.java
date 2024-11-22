package org.woven.hrms.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.woven.hrms.employee.entity.User;

public interface UserRepository extends JpaRepository<User,String> {
    @Query("SELECT u FROM User u left join fetch u.roles WHERE u.username = :username")
    User findByUsername(@Param("username") String username);


}
