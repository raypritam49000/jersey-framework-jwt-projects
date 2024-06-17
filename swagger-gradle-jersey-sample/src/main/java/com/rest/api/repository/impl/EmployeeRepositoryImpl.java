package com.rest.api.repository.impl;

import com.rest.api.entity.Employee;
import com.rest.api.repository.EmployeeRepository;
import com.rest.api.utility.HibernateUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    @Override
    public Employee save(Employee employee) {
        Transaction transaction = null;
        Serializable id = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            id = session.save(employee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return HibernateUtils.getSessionFactory().openSession().get(Employee.class, id);
    }

    @Override
    public Employee update(String id, Employee employee) {
        Transaction transaction = null;
        Employee existingEmployee = new Employee();
        Serializable sid = null;
        Employee updatedEmployee = new Employee();
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            existingEmployee = session.get(Employee.class, id);
            if (ObjectUtils.isNotEmpty(existingEmployee)) {
                existingEmployee.setName(employee.getName());
                existingEmployee.setDepartment(employee.getDepartment());
                existingEmployee.setSalary(employee.getSalary());
                existingEmployee.setCreatedDate(employee.getCreatedDate());
                existingEmployee.setUpdatedDate(employee.getUpdatedDate());
                updatedEmployee = (Employee) session.merge(existingEmployee);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return updatedEmployee;
    }

    @Override
    public void deleteById(String id) {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Employee employee = session.get(Employee.class, id);
            if (ObjectUtils.isNotEmpty(employee)) {
                session.delete(employee);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Employee findById(String id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.get(Employee.class, id);
        }
    }

    @Override
    public List<Employee> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query<Employee> query = session.createQuery("From Employee", Employee.class);
            return query.getResultList();
        }
    }
}
