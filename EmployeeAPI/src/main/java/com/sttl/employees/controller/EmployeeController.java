
package com.sttl.employees.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sttl.employees.model.CreateEmployeeDTO;
import com.sttl.employees.model.Department;
import com.sttl.employees.model.DepartmentDTO;
import com.sttl.employees.model.Employee;
import com.sttl.employees.model.EmployeeDTO;
import com.sttl.employees.repository.DepartmentRepository;
import com.sttl.employees.service.DepartmentService;
import com.sttl.employees.service.EmployeeService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService service;
    private final DepartmentService dservice;
    private final DepartmentRepository departmentRepository;

    public EmployeeController(EmployeeService service,DepartmentService dservice, DepartmentRepository departmentRepository) {
        this.service = service;
        this.dservice = dservice;
        this.departmentRepository = departmentRepository;
    }

	/**
	 * Handles GET requests to /employees. Retrieves a list of all employees.
	 *
	 * @return a list of EmployeeDTO objects representing all employees
	 */
    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return service.getAllEmployees();
    }
    
        /**
         * Handles GET requests to /employees/{empId}.
         * Retrieves an employee by their ID.
         * * @param empId the ID of the employee to retrieve
         * * @return
         */
    @GetMapping("/{empId}")
    public ResponseEntity<?> getEmployeesByid(@PathVariable String empId) {
    	EmployeeDTO employees = service.getEmployeeById(empId);
		if (employees == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found with ID: " + empId);
		}
        

        return ResponseEntity.ok(employees);
    }

	/**
	 * Handles GET requests to /employees/department/{deptId}. Retrieves a list of
	 * employees belonging to a specific department.
	 *
	 * @param deptId the ID of the department
	 * @return a list of EmployeeDTO objects representing employees in the specified
	 *         department
	 */
    @GetMapping("/department/{deptId}")
    public ResponseEntity<?> getEmployeesByDepartment(@PathVariable String deptId) {
    	List<EmployeeDTO> employees = service.getEmployeesByDepartmentAsDTO(deptId);

        
        return ResponseEntity.ok(employees);
    }

    
	/**
	 * Handles POST requests to /employees/department/{deptId}. Adds a new employee
	 * to a specific department.
	 *
	 * @param deptId the ID of the department
	 * @param dto    the CreateEmployeeDTO containing employee details
	 * @return a ResponseEntity indicating the result of the operation
	 */
    @PostMapping("/department/{deptId}")
    public ResponseEntity<?> addEmployeeToDepartment(
            @PathVariable String deptId,
            @Valid @RequestBody CreateEmployeeDTO dto) {

        // Optional: Check if dept exists before adding
       DepartmentDTO deptOpt = dservice.getDepartmentById(deptId);
        if (deptOpt == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Department ID '" + deptId + "' not found.");
        }
        
        Optional<Department> departmentOpt = departmentRepository.findById(deptOpt.id);
        if (departmentOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Department entity for ID '" + deptOpt.id + "' not found.");
        }

        EmployeeDTO edto= service.getEmployeeById(dto.id);
        
        if (edto != null) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee with ID '" + dto.id + "' already exists.");
        };
        
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body("Email is required.");
        }

        // Basic email format validation (checks for one '@' and one dot after '@')
        if (!dto.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            return ResponseEntity.badRequest().body("Invalid email format.");
        }
        
		if (dto.getName() == null || dto.getName().isBlank()) {
			return ResponseEntity.badRequest().body("Name is required.");
		}
		
		if (dto.getPosition() == null || dto.getPosition().isBlank()) {
			return ResponseEntity.badRequest().body("Position is required.");
		}
		if (dto.getId() == null || dto.getId().isBlank()) {
			return ResponseEntity.badRequest().body("ID is required.");
		}
		if (dto.getSalary() < 0) {
			return ResponseEntity.badRequest().body("Salary must be a positive number.");
		}

        Employee emp = new Employee();
        emp.setId(dto.id);
        emp.setName(dto.name);
        emp.setEmail(dto.email);
        emp.setPosition(dto.position);
        emp.setSalary(dto.salary);
        emp.setDepartment(departmentOpt.get());
        Employee saved = service.addEmployeeToDepartment(emp);
        
		EmployeeDTO employeeDTO = new EmployeeDTO(saved.getId(), saved.getName(), saved.getEmail(), saved.getPosition(),
				saved.getSalary());
        
        return ResponseEntity.status(HttpStatus.CREATED).body("Employee added to department ID '" + deptId + "': " + employeeDTO);
    }
	

   
    @DeleteMapping("/department/{deptId}/{empId}")
    public ResponseEntity<?> deleteEmployeeFromDepartment(@PathVariable String deptId, @PathVariable String empId) {
    	 EmployeeDTO edto= service.getEmployeeByIdAndDepartments(empId,deptId);
        if (edto == null) {
            return ResponseEntity.badRequest().body("Employee doesn't belong to this department or Employee not found");
        }
        service.deleteEmployeeFromDepartment(deptId, empId);
        return ResponseEntity.badRequest().body("Employee with ID '" + empId + "' deleted from department ID '" + deptId + "'.");
    }
    
	/**
	 * Handles DELETE requests to /department/{empId}. Deletes an employee from a emp id
	 * 
	 *
	 * @param empId the ID of the employee to delete
	 * @return a ResponseEntity indicating the result of the operation
	 */
    @DeleteMapping("/department/{empId}")
    public void deleteEmployeeFromDepartment1(@PathVariable String empId) {
    	 EmployeeDTO edto= service.getEmployeeById(empId);
        if (edto != null) {
        	service.deleteEmployeeFromDepartment("", empId);
        }
        
    }
}
