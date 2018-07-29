package de.slag.demo.data.test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceUnitUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import de.slag.demo.data.Demo;

public class HibernateDemo {

	String url;

	String user;

	String pw;

	String driverClass;

	String dialect;

	public static void main(String[] args) {
		Configuration configuration = new Configuration();

		configuration.addAnnotatedClass(Demo.class);
		configuration.setProperty("connection.driver_class", "org.mariadb.jdbc.Driver");
		configuration.setProperty("hibernate.connection.url", "jdbc:mysql://uranus:3306/SLGTS00DV1?serverTimezone=UTC");
		configuration.setProperty("hibernate.connection.username", "SLGTS00DV1");
		configuration.setProperty("hibernate.connection.password", "slag");
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

		configuration.setProperty("hibernate.hbm2ddl.auto", "update");
//		configuration.setProperty("javax.persistence.schema-generation.database.action", "create");

		// configuration.setProperty("show_sql", "true");
		// configuration.setProperty("hibernate.connection.pool_size", "10");

		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		out("build session factory...");
		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
		out("...fertig");
		
		createAndSaveNewEntity(sessionFactory);
		
		
		sessionFactory.close();
		out("closed!");

	}
	
	private static void createAndSaveNewEntity(SessionFactory sf) {
		Session session = sf.openSession();
		
		session.save(new Demo());
		
		session.close();
	}

	private static void executeManualQuery(Session openSession) {
		Transaction beginTransaction = openSession.beginTransaction();
		openSession
				.createSQLQuery("CREATE TABLE Persons ( " + "    PersonID int," + "    LastName varchar(255),"
						+ "    FirstName varchar(255)," + "    Address varchar(255)," + "    City varchar(255))")
				.executeUpdate();


		beginTransaction.commit();
	}

	private static void out(Object o) {
		System.out.println(o);
	}
}
