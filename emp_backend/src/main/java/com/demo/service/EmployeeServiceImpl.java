package com.demo.service;


import com.demo.model.Employee;


import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private CouchDbConnector couchDbConnector;

    @Override
    public String addEmployee(Employee employee) {
        //Validation check
    	validateEmployee(employee);
    	
    	// This ensures the generated ID is unique
        employee.setId(generateUniqueId());
        
        couchDbConnector.create(employee);
        return employee.getId1();
    }

    @Override
    public List<Employee> getAllEmployees(int page, int pageSize, String sortBy, String sortOrder) {
        // Calculate the skip based on pagination parameters
        int skip = (page - 1) * pageSize;

        // Define a ViewQuery with skip, limit, and sorting criteria
        ViewQuery query = new ViewQuery()
                .allDocs()
                .skip(skip)
                .limit(pageSize);
              
        // Execute the query and get the result
        ViewResult result = couchDbConnector.queryView(query);

        // Process the result and convert documents to Employee objects
        List<Employee> employees = new ArrayList<>();
        for (ViewResult.Row row : result.getRows()) {
            String docId = row.getId();
            Employee employee = couchDbConnector.get(Employee.class, docId);
            employees.add(employee);
        }
        
        // Sort the result based on the sortBy parameter using Java Streams
        if (sortBy != null && !sortBy.isEmpty()) {
            Comparator<Employee> comparator;
            if ("employeeName".equals(sortBy)) {
                comparator = Comparator.comparing(Employee::getEmployeeName);
            } else if ("email".equals(sortBy)) {
                comparator = Comparator.comparing(Employee::getEmail);
            } else {
                // Default to sorting by employeeName if sortBy is not recognized
                comparator = Comparator.comparing(Employee::getEmployeeName);
            }

			// Apply ascending or descending order
            if ("desc".equalsIgnoreCase(sortOrder)) {
                comparator = comparator.reversed();
            }

            employees = employees.stream().sorted(comparator).collect(Collectors.toList());
        }
        

        return employees;
    }
    
    @Override
    public Employee getEmployeeById(String id){
    	try {
            return couchDbConnector.get(Employee.class, id);
        } catch (Exception ex) {
        	// Log or Handle the error
            throw new EmployeeNotFoundException("Employee with ID " + id + " not found.");
        }
    }

    @Override
    public void deleteEmployeeById(String id) {
    	try {
            Employee employeeToDelete = couchDbConnector.get(Employee.class, id);

            if (employeeToDelete != null) {
                couchDbConnector.delete(employeeToDelete);
            } else {
            	//For when Employee with that ID is not Found
                throw new EmployeeNotFoundException("Employee with ID " + id + " not found");
            }
        } catch (Exception ex) {
        	// Log or Handle the error
            throw new EmployeeServiceException("Error deleting employee with ID " + id, ex);
        }
    }
    


    @Override
    public void updateEmployeeById(String id, Employee updatedEmployee) {
    	try {
            Employee existingEmployee = couchDbConnector.get(Employee.class, id);

            if (existingEmployee != null) {
                // Update the properties of the existing employee with the new values and then update employee
                existingEmployee.setEmployeeName(updatedEmployee.getEmployeeName());
                existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());
                existingEmployee.setEmail(updatedEmployee.getEmail());
                existingEmployee.setReportsTo(updatedEmployee.getReportsTo());
                existingEmployee.setProfileImage(updatedEmployee.getProfileImage());

                couchDbConnector.update(existingEmployee);
            } else {
            	//For when Employee with that ID is not Found
                throw new EmployeeNotFoundException("Employee with ID " + id + " not found");
            }
        } catch (Exception ex) {
            // Log or Handle the error
            throw new EmployeeServiceException("Error updating employee with ID " + id, ex);
        }
    }

    public Employee getNthLevelManager(String employeeId, int level) {
    	try {
            Employee employee = getEmployeeById(employeeId);

            if (employee == null) {
                throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found.");
            }

            if (level <= 0) {
                throw new IllegalArgumentException("Level should be greater than 0");
            }

            return findNthLevelManager(employee, level);
        } catch (Exception ex) {
            // Log or handle other exceptions as needed
            throw new EmployeeServiceException("Error getting nth level manager for employee with ID " + employeeId, ex);
        }
    }
    
    
    // Recursively find the (level-1)th manager
    private Employee findNthLevelManager(Employee employee, int level) {
        if (level == 1) {
            return getEmployeeById(employee.getReportsTo());
        } else if (employee.getReportsTo() != null) {
            
            return findNthLevelManager(getEmployeeById(employee.getReportsTo()), level - 1);
        }
        // If no manager found at the requested level
        return null;
    }
    
    
    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }
    
    
    //Validation Function
    private void validateEmployee(Employee employee) {
        if (employee.getEmployeeName() == null || employee.getEmployeeName().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty");
        }
    }
    
    //Custom Exceptions
    public class EmployeeNotFoundException extends RuntimeException {
        public EmployeeNotFoundException(String message) {
            super(message);
        }
    }
    
    public class EmployeeServiceException extends RuntimeException {
        public EmployeeServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}