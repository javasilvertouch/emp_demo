import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Employee {
  id: string;
  name: string;
  email: string;
  salary: number;
  position:string;
}

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private baseUrl = 'http://localhost:8080/';

  constructor(private http: HttpClient) {}

  getEmployeesByDept(deptId: string): Observable<Employee[]> {
    return this.http.get<Employee[]>(`${this.baseUrl}employees/department/${deptId}`);
  }

  getEmployeeById(empId: String): Observable<Employee> {
    return this.http.get<Employee>(`${this.baseUrl}employees/${empId}`);
  }

  getAllDept(): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/departments`);
  }

  deleteEmployee(empId: String): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}employees/department/${empId}`);
  }

  downloadEmployeesByDeptReport(): Observable<Blob> {
    return this.http.get(`${this.baseUrl}api/report/download`, { responseType: 'blob' });
  }

 updateEmployee(empId: string, employeeData: any): Observable<any> {
    return this.http.put(`${this.baseUrl}employees/edit/${empId}`, employeeData);
  }

  
}
