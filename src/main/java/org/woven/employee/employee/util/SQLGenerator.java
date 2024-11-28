package org.woven.employee.employee.util;

import org.woven.employee.employee.entity.Employee;
import org.woven.employee.employee.model.Gender;

import java.lang.reflect.Field;
import java.time.LocalDate;

public class SQLGenerator {

    public static String generateInsertSQL(final Object obj) {
        Class<?> clazz = obj.getClass();
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(clazz.getSimpleName().toLowerCase()).append(" (");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            sql.append(field.getName()).append(", ");
        }
        sql.setLength(sql.length() - 2); // Remove the last comma and space
        sql.append(") VALUES (");

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                if (value instanceof String) {
                    sql.append("'").append(value).append("', ");
                } else {
                    sql.append(value).append(", ");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sql.setLength(sql.length() - 2); // Remove the last comma and space
        sql.append(");");

        return sql.toString();
    }

    private static void printSql(final Employee employee) {
        String sql = generateInsertSQL(employee);
        System.out.println(sql);
    }

    public static void main(final String[] args) {

        Employee employee = new Employee(1L, "John","Doe", Gender.Male,
                LocalDate.EPOCH,LocalDate.EPOCH,
                "john.doe@example.com","Head Of operations");
        printSql(employee);
    }


}

