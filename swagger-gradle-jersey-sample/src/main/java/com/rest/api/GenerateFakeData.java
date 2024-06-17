package com.rest.api;

import com.github.javafaker.Faker;
import com.rest.api.entity.Employee;
import com.rest.api.utility.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Date;

public class GenerateFakeData {
    public static void main(String[] args) {

        // Create a Faker instance
        Faker faker = new Faker();

        // Open a session and begin a transaction
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();

            // Generate and save 1000 fake Employee entities
            for (int i = 0; i < 5000; i++) {
                Employee employee = new Employee();
                employee.setName(faker.name().fullName());
                employee.setDepartment(faker.company().industry());
                employee.setSalary(faker.number().randomDouble(2, 30000, 150000));
                employee.setCreatedDate(new Date());
                employee.setUpdatedDate(new Date());

                session.save(employee);

                // Optionally, flush and clear the session periodically to avoid memory issues
                if (i % 50 == 0) {
                    session.flush();
                    session.clear();
                }
            }

            // Commit the transaction
            session.getTransaction().commit();
        }

        System.out.println("1000 fake employees inserted successfully!");
    }
}
