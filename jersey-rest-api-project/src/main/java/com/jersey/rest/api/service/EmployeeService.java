package com.jersey.rest.api.service;

import com.jersey.rest.api.dto.EmployeeDTO;
import com.jersey.rest.api.search.BasePageDTO;

import java.util.Date;

public interface EmployeeService {
    public BasePageDTO<EmployeeDTO> searchEmployee(int pageNumber, int pageSize, String sortedField, String sortedDirection, String name, String department, Date startDate, Date endDate);
}
