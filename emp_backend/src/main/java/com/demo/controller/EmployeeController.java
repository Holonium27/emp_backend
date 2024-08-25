package com.demo.controller;

import com.demo.model.Employee;
import com.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add")
    public String addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @GetMapping("/getall/{page}/{pageSize}/{sortBy}/{sortOrder}")
    public List<Employee> getAllEmployees(@PathVariable int page, @PathVariable int pageSize, @PathVariable String sortBy, @PathVariable String sortOrder) {
        return employeeService.getAllEmployees(page, pageSize, sortBy, sortOrder);
    }
    
    @GetMapping("/get/{id}")
    public Employee getEmployeeById(@PathVariable String id) {
    	//System.out.println(id);
        return employeeService.getEmployeeById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEmployeeById(@PathVariable String id) {
    	//System.out.println(id);
        employeeService.deleteEmployeeById(id);
    }

    @PutMapping("/update/{id}")
    public void updateEmployeeById(@PathVariable String id, @RequestBody Employee updatedEmployee) {
    	//System.out.println(id);
        employeeService.updateEmployeeById(id, updatedEmployee);
    }
    
    @GetMapping("/nmanager/{id}/{level}")
    private Employee getNthLevelManager(@PathVariable String id, @PathVariable int level) {
    	return employeeService.getNthLevelManager(id, level);
    }
    
}
