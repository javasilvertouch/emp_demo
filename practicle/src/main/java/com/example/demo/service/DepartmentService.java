
package com.example.demo.service;

import java.util.List;

import com.example.demo.model.DepartmentDTO;

public interface DepartmentService {
    List<DepartmentDTO> getAllDepartments();
    DepartmentDTO getDepartmentById(String deptId);
}
