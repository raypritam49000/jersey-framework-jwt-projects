package com.jersey.rest.api.controller;

import com.jersey.rest.api.dto.EmployeeDTO;
import com.jersey.rest.api.filters.JWTTokenNeeded;
import com.jersey.rest.api.search.BasePageDTO;
import com.jersey.rest.api.service.EmployeeService;
import com.jersey.rest.api.service.impl.EmployeeServiceImpl;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Path("/employee")
@JWTTokenNeeded
public class EmployeeController {
    private final Logger LOGGER = Logger.getLogger(this.getClass());
    private final EmployeeService employeeService = new EmployeeServiceImpl();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @POST
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response searchEmployees(
            @DefaultValue("1") @QueryParam("pageNumber") int pageNumber,
            @DefaultValue("10") @QueryParam("pageSize") int pageSize,
            @DefaultValue("name") @QueryParam("sortedField") String sortedField,
            @DefaultValue("asc") @QueryParam("sortedDirection") String sortedDirection,
            @QueryParam("name") String name,
            @QueryParam("department") String department,
            @QueryParam("startDate") String startDateStr,
            @QueryParam("endDate") String endDateStr
    ) {

        LOGGER.info("Received search request with parameters - pageNumber : " + pageNumber + ", pageSize : " + pageSize + " ,sortedField : " + sortedField + ", sortedDirection :" + sortedDirection + ", name : " + name + ", department : " + department + ", startDateStr : " + startDateStr + ", endDateStr : " + endDateStr);

        Date startDate = parseDate(startDateStr);
        Date endDate = parseDate(endDateStr);
        BasePageDTO<EmployeeDTO> result = employeeService.searchEmployee(pageNumber, pageSize, sortedField, sortedDirection, name, department, startDate, endDate);
        return Response.ok(result).build();
    }

    private Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new WebApplicationException("Invalid date format, please use 'yyyy-MM-dd'", e, Response.Status.BAD_REQUEST);
        }
    }
}
