package org.woven.hrms.employee.service;

import org.woven.hrms.employee.model.LoginRequest;

public interface AuthService {
    String login(LoginRequest loginRequest);
}
