
package com.sttl.employees.service;

import java.util.List;

import com.sttl.employees.model.DepartmentDTO;

public interface DepartmentService {
    List<DepartmentDTO> getAllDepartments();
    DepartmentDTO getDepartmentById(String deptId);
}
