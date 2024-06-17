package com.rest.api.controller;

import com.google.common.collect.Maps;
import com.rest.api.dto.EmployeeDTO;
import com.rest.api.search.BasePageDTO;
import com.rest.api.service.EmployeeService;
import com.rest.api.service.impl.EmployeeServiceImpl;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Path("/employees")
public class EmployeeController {
    private final Logger LOGGER = Logger.getLogger(this.getClass());
    private final EmployeeService employeeService = new EmployeeServiceImpl();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEmployees() {
        LOGGER.info("Entering getAllEmployees method");
        List<EmployeeDTO> employees = employeeService.findAll();
        LOGGER.info("Exiting getAllEmployees method with result: " + employees);
        return Response.status(Response.Status.OK).status(Response.Status.OK.getStatusCode()).entity(employees).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeById(@PathParam("id") String id) {
        LOGGER.info("Entering getEmployeeById method with ID: " + id);
        EmployeeDTO employee = employeeService.findById(id);
        LOGGER.info("Exiting getEmployeeById method with result: " + employee);
        return Response.status(Response.Status.OK).status(Response.Status.OK.getStatusCode()).entity(employee).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEmployee(EmployeeDTO employeeDTO) throws ParseException {
        LOGGER.info("Entering createEmployee method with EmployeeDTO: " + employeeDTO);
        employeeDTO.setCreatedDate(getCurrentFormattedDate());
        employeeDTO.setUpdatedDate(getCurrentFormattedDate());
        EmployeeDTO createdEmployee = employeeService.save(employeeDTO);
        LOGGER.info("Exiting createEmployee method with result: " + createdEmployee);
        return Response.status(Response.Status.CREATED).status(Response.Status.CREATED.getStatusCode()).entity(createdEmployee).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEmployee(@PathParam("id") String id, EmployeeDTO employeeDTO) throws ParseException {
        LOGGER.info("Entering updateEmployee method with ID: " + id + " and EmployeeDTO: " + employeeDTO);
        employeeDTO.setCreatedDate(getCurrentFormattedDate());
        employeeDTO.setUpdatedDate(getCurrentFormattedDate());
        EmployeeDTO updatedEmployee = employeeService.update(id, employeeDTO);
        LOGGER.info("Exiting updateEmployee method with result: " + updatedEmployee);
        return Response.status(Response.Status.OK).status(Response.Status.OK.getStatusCode()).entity(updatedEmployee).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteEmployee(@PathParam("id") String id) {
        LOGGER.info("Entering deleteEmployee method with ID: " + id);
        employeeService.delete(id);
        LOGGER.info("Exiting deleteEmployee method for ID: " + id);
        Map<String, Object> response = Maps.newHashMap();
        response.put("status", Response.Status.OK);
        response.put("statusCode", Response.Status.OK.getStatusCode());
        response.put("message", "Employee deleted successfully");
        return Response.status(Response.Status.OK).status(Response.Status.OK.getStatusCode()).entity(response).build();
    }

    @GET
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
            @QueryParam("endDate") String endDateStr,
            @QueryParam("salary") Double salary
    ) {

        LOGGER.info("------------ Received search request with parameters - pageNumber : " + pageNumber + ", pageSize : " + pageSize + " ,sortedField : " + sortedField + ", sortedDirection :" + sortedDirection + ", name : " + name + ", department : " + department + ", salary : " + salary + ", startDateStr : " + startDateStr + ", endDateStr : " + endDateStr);

        Date startDate = parseDate(startDateStr);
        Date endDate = parseDate(endDateStr);
        BasePageDTO<EmployeeDTO> result = employeeService.searchEmployee(pageNumber, pageSize, sortedField, sortedDirection, name, department, salary, startDate, endDate);
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

    public static Date getCurrentFormattedDate() throws ParseException {
        // Format for date conversion
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // Get the current date
        Date currentDate = new Date();
        // Format the current date to match the desired format
        String formattedDate = dateFormat.format(currentDate);
        // Parse the formatted date string back to a Date object
        return dateFormat.parse(formattedDate);
    }
}
