
package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Department;
import com.example.demo.model.Employee;
import com.example.demo.model.EmployeeDTO;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepo;
    private final DepartmentRepository departmentRepo;

    public EmployeeServiceImpl(EmployeeRepository employeeRepo, DepartmentRepository departmentRepo) {
        this.employeeRepo = employeeRepo;
        this.departmentRepo = departmentRepo;
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
    	return employeeRepo.findAll().stream().map(emp ->
        new EmployeeDTO(emp.getId(), emp.getName(), emp.getEmail(), emp.getPosition(), emp.getSalary())
    ).toList();
    }


    @Override
    public List<EmployeeDTO> getEmployeesByDepartmentAsDTO(String deptId) {
        return employeeRepo.findByDepartmentId(deptId)
            .stream()
            .map(emp -> new EmployeeDTO(
                emp.getId(),
                emp.getName(),
                emp.getEmail(),
                emp.getPosition(),
                emp.getSalary()
            ))
            .collect(Collectors.toList());
    }


    @Transactional
    @Override
    public Employee addEmployeeToDepartment(Employee employee) {        
        return employeeRepo.save(employee);
    }

    @Transactional
    @Override
    public void deleteEmployeeFromDepartment(String deptId, String empId) {
        Employee emp = employeeRepo.findById(empId).orElseThrow();
		/*
		 * if (!emp.getDepartment().getId().equals(deptId)) { throw new
		 * RuntimeException("Employee doesn't belong to this department"); }
		 */
        employeeRepo.delete(emp);
    }

	@Override
	public EmployeeDTO getEmployeeById(String empId) {
		Employee emp = employeeRepo.findById(empId).orElse(null);
		if (emp == null) {
			return null; 
		}
		return new EmployeeDTO(emp.getId(), emp.getName(), emp.getEmail(), emp.getPosition(), emp.getSalary());
	}

	@Override
	public EmployeeDTO getEmployeeByIdAndDepartments(String empId, String deptId) {
		Employee emp = employeeRepo.findByIdAndDepartmentId(empId,deptId);
		if (emp == null || !emp.getDepartment().getId().equals(deptId)) {
			return null;
		}
		return new EmployeeDTO(emp.getId(), emp.getName(), emp.getEmail(), emp.getPosition(), emp.getSalary());
	}
}
