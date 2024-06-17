package com.jersey.rest.api.service.impl;

import com.jersey.rest.api.dto.EmployeeDTO;
import com.jersey.rest.api.entity.Employee;
import com.jersey.rest.api.search.BasePageDTO;
import com.jersey.rest.api.search.PaginationFilterUtils;
import com.jersey.rest.api.search.PaginationSortingUtils;
import com.jersey.rest.api.search.PaginationUtils;
import com.jersey.rest.api.service.EmployeeService;
import com.jersey.rest.api.utils.HibernateUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public BasePageDTO<EmployeeDTO> searchEmployee(int pageNumber, int pageSize, String sortedField, String sortedDirection, String name, String department, Date startDate, Date endDate) {
        try {
            SessionFactory sessionFactory = HibernateUtils.getsessionFactory();
            Session session = sessionFactory.openSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
            Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
            criteriaQuery.select(employeeRoot);

            // Build predicates for filtering
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotEmpty(name)) {
                predicates.add(PaginationFilterUtils.applyLikeFilter(criteriaBuilder, employeeRoot, "name", name));
            }

            if (StringUtils.isNotEmpty(department)) {
                predicates.add(PaginationFilterUtils.applyEqualFilter(criteriaBuilder, employeeRoot, "department", department));
            }

            if (ObjectUtils.isNotEmpty(startDate) && ObjectUtils.isNotEmpty(endDate)) {
                predicates.add(PaginationFilterUtils.applyBetweenFilter(criteriaBuilder, employeeRoot, "createdDate", startDate, endDate));
            } else if (ObjectUtils.isNotEmpty(startDate)) {
                predicates.add(PaginationFilterUtils.getGreaterThanOrEqualToPredicate(criteriaBuilder, employeeRoot, "createdDate", startDate));
            } else if (ObjectUtils.isNotEmpty(endDate)) {
                predicates.add(PaginationFilterUtils.getLessThanOrEqualToPredicate(criteriaBuilder, employeeRoot, "createdDate", endDate));
            }

            // Apply predicates to the criteria query
            criteriaQuery.where(predicates.toArray(new Predicate[0]));

            // Set sorting parameters
            PaginationSortingUtils.applySorting(criteriaBuilder, criteriaQuery, employeeRoot, sortedField, sortedDirection);

            // Set pagination parameters
            int startRecord = (pageNumber - 1) * pageSize;

            // Execute the query
            Query<Employee> query = session.createQuery(criteriaQuery);
            query.setFirstResult(startRecord);
            query.setMaxResults(pageSize);
            List<Employee> resultList = query.getResultList();
            List<EmployeeDTO> employeeDTOS = resultList.stream().map(employee -> new ModelMapper().map(employee, EmployeeDTO.class)).collect(Collectors.toList());

            // Count total filter elements
            Long totalFilterElements = PaginationUtils.countTotalFilterElements(session, criteriaBuilder, predicates, Employee.class);

            // Count total elements
            Long totalElements = PaginationUtils.countTotalElements(session, criteriaBuilder, Employee.class);

            // Calculate total pages
            int totalPages = (int) Math.ceil((double) totalFilterElements / pageSize);

            // Create BasePageDTO instance
            return PaginationUtils.createBasePageDTO(employeeDTOS, pageNumber, pageSize, totalElements, totalFilterElements, totalPages, sortedField, sortedDirection, "Employee Details");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new BasePageDTO<EmployeeDTO>();
    }
}
