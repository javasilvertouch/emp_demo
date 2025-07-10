
package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Employee;
import com.example.demo.model.EmployeeDTO;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployees();
    List<EmployeeDTO> getEmployeesByDepartmentAsDTO(String deptId);
    Employee addEmployeeToDepartment(Employee employee);
    void deleteEmployeeFromDepartment(String deptId, String empId);
    EmployeeDTO getEmployeeById(String empId);    
    EmployeeDTO getEmployeeByIdAndDepartments(String empId,String deptId);
    
    
}
