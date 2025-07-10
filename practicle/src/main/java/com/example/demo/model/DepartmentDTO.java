package com.example.demo.model;

import java.util.List;

public class DepartmentDTO {
    public String id;
    public String name;
    public String location;
    public List<EmployeeDTO> employees;

    public DepartmentDTO(String id, String name, String location, List<EmployeeDTO> employees) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.employees = employees;
    }
}
