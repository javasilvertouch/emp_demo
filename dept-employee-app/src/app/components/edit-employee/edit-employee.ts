import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { EmployeeService } from '../../services/employee'; // ✅ adjust the path if needed

@Component({
  selector: 'app-edit-employee',
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule
  ],
  templateUrl: './edit-employee.html',
  styleUrls: ['./edit-employee.css']
})
export class EditEmployee implements OnInit {
  employeeForm!: FormGroup;
  empId!: string;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private employeeService: EmployeeService
  ) {}

  ngOnInit(): void {
    this.empId = this.route.snapshot.paramMap.get('id')!;
       this.employeeService.getEmployeeById(this.empId).subscribe(emp => {
      this.employeeForm = this.fb.group({
        name: [emp.name, Validators.required],
        email: [emp.email, [Validators.required, Validators.email]],
        position: [emp.position, Validators.required],
        salary: [emp.salary, [Validators.required, Validators.min(0)]],
      });
    });
  }

  onSubmit(): void {
    if (this.employeeForm.valid) {
      this.employeeService.updateEmployee(this.empId, this.employeeForm.value).subscribe(() => {
        alert('Employee updated successfully!');
        this.router.navigate(['/']);
      });
    }
  }

  goBack(): void {
    this.router.navigate(['/']);
  }
}
