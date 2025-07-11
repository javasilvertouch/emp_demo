
package com.sttl.employees.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sttl.employees.model.DepartmentDTO;
import com.sttl.employees.model.EmployeeDTO;
import com.sttl.employees.repository.DepartmentRepository;
import com.sttl.employees.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
    private  DepartmentRepository repository;
	
 
	/**
	 * Retrieves all departments and their employees.
	 *
	 * @return a list of DepartmentDTO objects containing department details and
	 *         associated employees
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

    
    /**
     * Retrieves a department by its ID along with all associated employees.
     *
     * @param deptId the ID of the department to retrieve
     * @return a DepartmentDTO containing department details and its employees,
     *         or null if the department is not found
     */
	@Override
	public DepartmentDTO getDepartmentById(String deptId) {
		return repository.findById(deptId).map(dept -> {
			List<EmployeeDTO> employeeDTOs = dept.getEmployees().stream().map(emp -> new EmployeeDTO(emp.getId(),
					emp.getName(), emp.getEmail(), emp.getPosition(), emp.getSalary())).toList();

			return new DepartmentDTO(dept.getId(), dept.getName(), dept.getLocation(), employeeDTOs);
		}).orElse(null);
	}
}
