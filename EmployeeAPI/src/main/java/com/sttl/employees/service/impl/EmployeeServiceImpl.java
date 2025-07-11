
package com.sttl.employees.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sttl.employees.model.Employee;
import com.sttl.employees.model.EmployeeDTO;
import com.sttl.employees.repository.EmployeeRepository;
import com.sttl.employees.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepo;
    

    public EmployeeServiceImpl(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

	/**
	 * Retrieves all employees and converts them to EmployeeDTO objects.
	 *
	 * @return a list of EmployeeDTO objects representing all employees
	 */
    @Override
    public List<EmployeeDTO> getAllEmployees() {
    	return employeeRepo.findAll().stream().map(emp ->
        new EmployeeDTO(emp.getId(), emp.getName(), emp.getEmail(), emp.getPosition(), emp.getSalary())
    ).toList();
    }


    /**
     * Retrieves a list of employees belonging to a specific department
     * and maps them to EmployeeDTO objects.
     *
     * @param deptId the ID of the department whose employees are to be retrieved
     * @return a list of EmployeeDTOs for the given department ID
     */
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


	/**
	 * Adds a new employee to the specified department.
	 *
	 * @param employee the Employee object to be added
	 * @return the saved Employee object
	 */
    @Transactional
    @Override
    public Employee addEmployeeToDepartment(Employee employee) {        
        return employeeRepo.save(employee);
    }

    
   /**
    * Deletes an employee from the specified department.
    *
    * @param deptId the ID of the department (not directly used in this method but may be used for validation in future)
    * @param empId the ID of the employee to be deleted
    */
    @Transactional
    @Override
    public void deleteEmployeeFromDepartment(String deptId, String empId) {
        Employee emp = employeeRepo.findById(empId).orElse(null);
        if(emp !=null)
        	employeeRepo.delete(emp);
    }

    /**
     * Retrieves an employee by their ID and maps the entity to an EmployeeDTO.
     *
     * @param empId the ID of the employee to retrieve
     * @return an EmployeeDTO containing employee details, or null if not found
     */
	@Override
	public EmployeeDTO getEmployeeById(String empId) {
		Employee emp = employeeRepo.findById(empId).orElse(null);
		if (emp == null) {
			return null; 
		}
		return new EmployeeDTO(emp.getId(), emp.getName(), emp.getEmail(), emp.getPosition(), emp.getSalary());
	}

	/**
	 * Retrieves an employee by their ID and department ID.
	 *
	 * @param empId  the ID of the employee to retrieve
	 * @param deptId the ID of the department to which the employee belongs
	 * @return an EmployeeDTO containing employee details, or null if not found or
	 *         does not belong to the specified department
	 */
	@Override
	public EmployeeDTO getEmployeeByIdAndDepartments(String empId, String deptId) {
		Employee emp = employeeRepo.findByIdAndDepartmentId(empId,deptId);
		if (emp == null || !emp.getDepartment().getId().equals(deptId)) {
			return null;
		}
		return new EmployeeDTO(emp.getId(), emp.getName(), emp.getEmail(), emp.getPosition(), emp.getSalary());
	}
}
