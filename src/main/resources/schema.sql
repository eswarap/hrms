  create table if not exists employee (
        "id" bigint not null,
        "birth_date" date,
        "email" varchar(255),
        "first_name" varchar(255),
        "gender" tinyint check ("gender" between 0 and 2),
        "joining_date" date,
        "last_name" varchar(255),
        "position" varchar(255),
        primary key ("id")
    );

CREATE SEQUENCE EMPLOYEE_SEQUENCE
 MINVALUE 1
 START WITH 50
 INCREMENT BY 2;