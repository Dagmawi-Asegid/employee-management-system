package com.dagmawiasegid.employeemanagement.service;

import com.dagmawiasegid.employeemanagement.model.Employee;

import java.util.List;

/**
 * Business logic contract for employee management, sitting between the
 * REST controller layer and the JPA repository layer.
 */
public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);

    Employee createEmployee(Employee employee);

    Employee updateEmployee(Long id, Employee employee);

    void deleteEmployee(Long id);
}
