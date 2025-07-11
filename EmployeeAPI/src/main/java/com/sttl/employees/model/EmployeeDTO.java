package com.sttl.employees.model;

import lombok.Data;

@Data
public class EmployeeDTO {
    public String id;
    public String name;
    public String email;
    public String position;
    public double salary;

    // constructor
    public EmployeeDTO(String id, String name, String email, String position, double salary) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.position = position;
        this.salary = salary;
    }
}