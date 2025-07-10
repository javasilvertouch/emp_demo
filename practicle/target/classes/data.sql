INSERT INTO DEPARTMENT (id, name, location) VALUES
('dept01', 'Human Resources', 'Building A'),
('dept02', 'Engineering', 'Building B');

INSERT INTO EMPLOYEE (id, name, email, position, salary, department_id) VALUES
('emp001', 'Alice Smith', 'alice.smith@example.com', 'Recruiter', 60000, 'dept01'),
('emp003', 'Charlie Brown', 'charlie.brown@example.com', 'HR Assistant', 40000, 'dept01'),
('emp002', 'Bob Johnson', 'bob.johnson@example.com', 'Software Engineer', 80000, 'dept02'),
('emp004', 'Diana Prince', 'diana.prince@example.com', 'System Architect', 90000, 'dept02');