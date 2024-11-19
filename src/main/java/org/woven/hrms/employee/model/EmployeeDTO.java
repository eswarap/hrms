package org.woven.hrms.employee.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Getter
@Setter
public class EmployeeDTO {

    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate birthDate;
    private LocalDate joiningDate;
    private String email;
    private String position;


}


