package com.dagmawiasegid.employeemanagement.repository;

import com.dagmawiasegid.employeemanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for {@link Employee} entities.
 * Handles all direct database access for employee records.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    boolean existsByEmail(String email);
}
