package com.dagmawiasegid.employeemanagement.service;

import com.dagmawiasegid.employeemanagement.exception.DuplicateEmailException;
import com.dagmawiasegid.employeemanagement.exception.ResourceNotFoundException;
import com.dagmawiasegid.employeemanagement.model.Employee;
import com.dagmawiasegid.employeemanagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Default implementation of {@link EmployeeService}. Contains the actual
 * business rules (uniqueness checks, not-found handling) and delegates
 * persistence to {@link EmployeeRepository}.
 */
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    @Override
    public Employee createEmployee(Employee employee) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new DuplicateEmailException("An employee with email " + employee.getEmail() + " already exists");
        }
        employee.setId(null);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee updated) {
        Employee existing = getEmployeeById(id);

        employeeRepository.findByEmail(updated.getEmail())
                .filter(other -> !other.getId().equals(id))
                .ifPresent(other -> {
                    throw new DuplicateEmailException("An employee with email " + updated.getEmail() + " already exists");
                });

        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setEmail(updated.getEmail());
        existing.setJobTitle(updated.getJobTitle());
        existing.setDepartment(updated.getDepartment());
        existing.setSalary(updated.getSalary());
        existing.setHireDate(updated.getHireDate());

        return employeeRepository.save(existing);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee existing = getEmployeeById(id);
        employeeRepository.delete(existing);
    }
}
