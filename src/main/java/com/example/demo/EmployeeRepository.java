package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository gives you: save, findById, findAll, deleteById, etc.
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Extra finder to help us enforce unique email later
    Optional<Employee> findByEmail(String email);
}
