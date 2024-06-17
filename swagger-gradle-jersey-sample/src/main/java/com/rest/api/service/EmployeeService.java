package com.rest.api.service;

import com.rest.api.dto.EmployeeDTO;
import com.rest.api.search.BasePageDTO;

import java.util.Date;
import java.util.List;

public interface EmployeeService {
    EmployeeDTO save(EmployeeDTO employeeDTO);
    EmployeeDTO update(String id,EmployeeDTO employeeDTO);
    void delete(String id);
    EmployeeDTO findById(String id);
    List<EmployeeDTO> findAll();
    public BasePageDTO<EmployeeDTO> searchEmployee(int pageNumber, int pageSize, String sortedField, String sortedDirection, String name, String department,Double salary, Date startDate, Date endDate);

}
