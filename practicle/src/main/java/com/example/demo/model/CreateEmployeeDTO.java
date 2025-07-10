package com.example.demo.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateEmployeeDTO {
    @NotBlank(message = "ID is required")
    public String id;

    @NotBlank(message = "Name is required")
    public String name;

    @Pattern(
    	    regexp = "^(?!.*@.*@)[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
    	    message = "Email must be valid and contain only one '@' and a valid domain"
    	)
    @NotBlank(message = "Email is required")
    public String email;

    @NotBlank(message = "Position is required")
    public String position;

    @Min(value = 0, message = "Salary must be a positive number")
    public double salary;

}
