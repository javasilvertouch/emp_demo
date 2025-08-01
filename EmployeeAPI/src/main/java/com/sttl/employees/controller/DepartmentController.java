
package com.sttl.employees.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sttl.employees.model.DepartmentDTO;
import com.sttl.employees.service.DepartmentService;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

	/**
	 * Handles GET requests to /departments. Retrieves a list of all departments.
	 *
	 * @return a list of DepartmentDTO objects representing all departments
	 */
    @GetMapping
    public List<DepartmentDTO> getAllDepartments() {
        return service.getAllDepartments();
    }
}
