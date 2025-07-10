
package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.DepartmentDTO;
import com.example.demo.model.EmployeeDTO;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
    private  DepartmentRepository repository;

	/*
	 * public DepartmentServiceImpl(DepartmentRepository repository) {
	 * this.repository = repository; }
	 */
 
    
    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return repository.findAll().stream().map(dept -> {
            List<EmployeeDTO> employeeDTOs = dept.getEmployees().stream().map(emp ->
                new EmployeeDTO(emp.getId(), emp.getName(), emp.getEmail(), emp.getPosition(), emp.getSalary())
            ).toList();

            return new DepartmentDTO(dept.getId(), dept.getName(), dept.getLocation(), employeeDTOs);
        }).toList();
    }

	@Override
	public DepartmentDTO getDepartmentById(String deptId) {
		return repository.findById(deptId).map(dept -> {
			List<EmployeeDTO> employeeDTOs = dept.getEmployees().stream().map(emp -> new EmployeeDTO(emp.getId(),
					emp.getName(), emp.getEmail(), emp.getPosition(), emp.getSalary())).toList();

			return new DepartmentDTO(dept.getId(), dept.getName(), dept.getLocation(), employeeDTOs);
		}).orElse(null);
	}
}
