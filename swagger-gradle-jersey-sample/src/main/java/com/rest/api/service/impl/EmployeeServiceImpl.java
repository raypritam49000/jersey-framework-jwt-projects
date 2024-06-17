package com.rest.api.service.impl;

import com.rest.api.dto.EmployeeDTO;
import com.rest.api.entity.Employee;
import com.rest.api.repository.EmployeeRepository;
import com.rest.api.repository.impl.EmployeeRepositoryImpl;
import com.rest.api.search.BasePageDTO;
import com.rest.api.search.PaginationFilterUtils;
import com.rest.api.search.PaginationSortingUtils;
import com.rest.api.search.PaginationUtils;
import com.rest.api.service.EmployeeService;
import com.rest.api.utility.HibernateUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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
    private final Logger LOGGER = Logger.getLogger(this.getClass());
    private final ModelMapper modelMapper = new ModelMapper();
    private final EmployeeRepository employeeRepository = new EmployeeRepositoryImpl();

    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        return modelMapper.map(employeeRepository.save(modelMapper.map(employeeDTO, Employee.class)), EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO update(String id, EmployeeDTO employeeDTO) {
        return modelMapper.map(employeeRepository.update(id, modelMapper.map(employeeDTO, Employee.class)), EmployeeDTO.class);
    }

    @Override
    public void delete(String id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeDTO findById(String id) {
        return modelMapper.map(employeeRepository.findById(id), EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> findAll() {
        return employeeRepository.findAll().stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class)).collect(Collectors.toList());
    }

    @Override
    public BasePageDTO<EmployeeDTO> searchEmployee(int pageNumber, int pageSize, String sortedField, String sortedDirection, String name, String department, Double salary, Date startDate, Date endDate) {
        LOGGER.info("-------- Search Employee with parameters - pageNumber : " + pageNumber + ", pageSize : " + pageSize + " ,sortedField : " + sortedField + ", sortedDirection :" + sortedDirection + ", name : " + name + ", department : " + department + ", salary : " + salary + ", startDateStr : " + startDate + ", endDateStr : " + endDate);

        try {
            SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
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

            if (ObjectUtils.isNotEmpty(salary)) {
                predicates.add(PaginationFilterUtils.applyEqualSalaryFilter(criteriaBuilder, employeeRoot, "salary", salary));
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
