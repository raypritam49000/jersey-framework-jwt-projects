package com.rest.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private String id;
    private String name;
    private String department;
    private Double salary;
    private Date createdDate;
    private Date updatedDate;
}
