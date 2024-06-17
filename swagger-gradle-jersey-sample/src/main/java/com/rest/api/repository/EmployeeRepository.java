package com.rest.api.repository;

import com.rest.api.entity.Employee;

import java.util.List;

public interface EmployeeRepository {
    Employee save(Employee employee);
    Employee update(String id,Employee employee);
    public void deleteById(String id);
    Employee findById(String id);
    List<Employee> findAll();
}
