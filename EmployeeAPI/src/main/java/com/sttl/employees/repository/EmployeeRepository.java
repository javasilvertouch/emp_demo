
package com.sttl.employees.repository;

import com.sttl.employees.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    List<Employee> findByDepartmentId(String departmentId);
    Employee findByIdAndDepartmentId(String empId, String deptId);
}
