package com.sttl.employees.model;

import java.util.List;

public class DepartmentsWrapper {
    public List<DepartmentDTO> departments;

    public DepartmentsWrapper(List<DepartmentDTO> departments) {
        this.departments = departments;
    }
}

