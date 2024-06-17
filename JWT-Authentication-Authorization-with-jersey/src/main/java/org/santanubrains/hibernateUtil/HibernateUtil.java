package org.santanubrains.hibernateUtil;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

	public static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {

		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		try {

			return new MetadataSources(registry).buildMetadata().buildSessionFactory();

		} catch (Exception e) {
			e.printStackTrace();
			StandardServiceRegistryBuilder.destroy(registry);
			throw new RuntimeException("There was a error building the factory");
		}

	}

	public static SessionFactory getSessionfactory() {
		return sessionFactory;
	}

}
