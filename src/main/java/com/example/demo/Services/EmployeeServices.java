package com.example.demo.Services;

import com.example.demo.Entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EmployeeServices {
    Page<Employee> findAll(Pageable pageable);

    List<Employee> findAll();

    <S extends Employee> S save(S entity);

    Optional<Employee> findById(Integer integer);

    void deleteById(Integer integer);
}
