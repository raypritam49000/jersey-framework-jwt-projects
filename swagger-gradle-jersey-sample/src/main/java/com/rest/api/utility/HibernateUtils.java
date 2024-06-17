package com.rest.api.utility;

import com.rest.api.entity.Employee;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class HibernateUtils {
	private static SessionFactory sessionFactory = null;

	private HibernateUtils() {

	}

	public static SessionFactory getSessionFactory() {
		if (!ObjectUtils.isNotEmpty(sessionFactory)) {
			synchronized (HibernateUtils.class) {
				if (!ObjectUtils.isNotEmpty(sessionFactory)) {
					try {
						Configuration configuration = new Configuration();
						Properties settings = new Properties();

						settings.put(Environment.DRIVER, PropertyFileReader.getPropertyValue("db.driver-class-name"));
						settings.put(Environment.URL, PropertyFileReader.getPropertyValue("db.url"));
						settings.put(Environment.USER, PropertyFileReader.getPropertyValue("db.username"));
						settings.put(Environment.PASS, PropertyFileReader.getPropertyValue("db.password"));

						settings.put(Environment.DIALECT, PropertyFileReader.getPropertyValue("hibernate.dialect"));
						settings.put(Environment.SHOW_SQL, PropertyFileReader.getPropertyValue("hibernate.show_sql"));
						settings.put(Environment.FORMAT_SQL, PropertyFileReader.getPropertyValue("hibernate.format_sql"));
						settings.put(Environment.HBM2DDL_AUTO, PropertyFileReader.getPropertyValue("hibernate.hbm2ddl.auto"));

						settings.put(Environment.CONNECTION_PROVIDER, "org.hibernate.c3p0.internal.C3P0ConnectionProvider");
						settings.put(Environment.C3P0_MIN_SIZE, "0");
						settings.put(Environment.C3P0_MAX_SIZE, "1450");
						settings.put(Environment.C3P0_ACQUIRE_INCREMENT, "5");
						settings.put(Environment.C3P0_TIMEOUT, "180");
						settings.put(Environment.C3P0_MAX_STATEMENTS, "100");
						settings.put(Environment.C3P0_IDLE_TEST_PERIOD, "120");
						settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

						configuration.setProperties(settings);
						 configuration.addAnnotatedClass(Employee.class);
						sessionFactory = configuration.buildSessionFactory();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return sessionFactory;
	}

	public static synchronized SessionFactory getSessionFactoryProvider() {
		if (!ObjectUtils.isNotEmpty(sessionFactory)) {
			try {
				Configuration configuration = new Configuration();
				Properties settings = new Properties();

				settings.put(Environment.DRIVER, PropertyFileReader.getPropertyValue("db.driver-class-name"));
				settings.put(Environment.URL, PropertyFileReader.getPropertyValue("db.url"));
				settings.put(Environment.USER, PropertyFileReader.getPropertyValue("db.username"));
				settings.put(Environment.PASS, PropertyFileReader.getPropertyValue("db.password"));

				settings.put(Environment.DIALECT, PropertyFileReader.getPropertyValue("hibernate.dialect"));
				settings.put(Environment.SHOW_SQL, PropertyFileReader.getPropertyValue("hibernate.show_sql"));
				settings.put(Environment.FORMAT_SQL, PropertyFileReader.getPropertyValue("hibernate.format_sql"));
				settings.put(Environment.HBM2DDL_AUTO, PropertyFileReader.getPropertyValue("hibernate.hbm2ddl.auto"));

				settings.put(Environment.CONNECTION_PROVIDER, "org.hibernate.c3p0.internal.C3P0ConnectionProvider");
				settings.put(Environment.C3P0_MIN_SIZE, "0");
				settings.put(Environment.C3P0_MAX_SIZE, "1450");
				settings.put(Environment.C3P0_ACQUIRE_INCREMENT, "5");
				settings.put(Environment.C3P0_TIMEOUT, "180");
				settings.put(Environment.C3P0_MAX_STATEMENTS, "100");
				settings.put(Environment.C3P0_IDLE_TEST_PERIOD, "120");
				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

				configuration.setProperties(settings);
				 configuration.addAnnotatedClass(Employee.class);
				sessionFactory = configuration.buildSessionFactory();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}

	public static void main(String[] args) {
		System.out.println(HibernateUtils.getSessionFactory());
		System.out.println(HibernateUtils.getSessionFactoryProvider());
	}
}
