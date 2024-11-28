package org.woven.employee.employee.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.woven.employee.employee.model.Gender;

import java.time.LocalDate;
import java.util.Objects;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "EMPLOYEE")
public class Employee implements Comparable<Employee> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="gender")
    private Gender gender;

    @Column(name="birth_date")
    private LocalDate birthDate;

    @Column(name="joining_date")
    private LocalDate joiningDate;

    @Column(name="email")
    private String email;

    @Column(name="position")
    private String position;

    @Override
    public int compareTo(final Employee other) {
        int result = 0;
        String thisFirstName = this.getFirstName().toLowerCase();
        String otherFirstName = other.getFirstName().toLowerCase();
        if (!thisFirstName.equals(otherFirstName)) {
            result = thisFirstName.compareTo(otherFirstName);
        } else {
            String thisLastName = this.getLastName().toLowerCase();
            String otherLastName = other.getLastName().toLowerCase();
            result = thisLastName.compareTo(otherLastName);
        }
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee1 = (Employee) o;
        return Objects.equals(firstName, employee1.firstName) && Objects.equals(lastName, employee1.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
