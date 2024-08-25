package com.demo.service;

import com.demo.model.Employee;

import java.util.List;

public interface EmployeeService {

    String addEmployee(Employee employee);
    
    Employee getEmployeeById(String id);

    List<Employee> getAllEmployees(int page, int pageSize, String sortBy, String sortOrder);

    void deleteEmployeeById(String id);

    void updateEmployeeById(String id, Employee updatedEmployee);
    
    Employee getNthLevelManager(String employeeId, int level);
}
