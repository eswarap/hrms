package org.woven.hrms.employee.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private String message;
    private List<String> authorities;

}
