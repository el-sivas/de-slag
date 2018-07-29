package xy.test;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateDemo {

	public static void main(String[] args) {
		Configurator.setLevel("org.hibernate", Level.INFO);

		// just validate

		// no validation
		final SessionFactory sessionFactory1 = createSessionFactory(false);
		sessionFactory1.close();

		SessionFactory sessionFactory = createSessionFactory(true);

		out("session factory created");

		final Session session = sessionFactory.openSession();

		// manual query to test connection and db user rights
		final Transaction beginTransaction = session.beginTransaction();
		final String queryString = "CREATE TABLE test_table (test_column varchar(255))";
		session.createSQLQuery(queryString).executeUpdate();
		beginTransaction.commit();

		session.save(new XyEntity());

		session.close();
		sessionFactory.close();
	}

	private static SessionFactory createSessionFactory(boolean validate) {
		Configuration configuration = new Configuration();

		configuration.addAnnotatedClass(XyEntity.class);
		configuration.setProperty("hibernate.connection.driver_class", "org.mariadb.jdbc.Driver");
		configuration.setProperty("hibernate.connection.url", "jdbc:mysql://uranus:3306/test?serverTimezone=UTC");
		configuration.setProperty("hibernate.connection.username", "test");
		configuration.setProperty("hibernate.connection.password", "test");
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDB53Dialect");

		configuration.setProperty("hibernate.show_sql", "true");
		configuration.setProperty("hibernate.connection.pool_size", "10");

		if (validate) {
			configuration.setProperty("hibernate.hbm2ddl.auto", "validate");
		} else {
			configuration.setProperty("hibernate.hbm2ddl.auto", "update");
		}

		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());

		out("build session factory. validate: " + validate);
		SessionFactory buildSessionFactory = configuration.buildSessionFactory(builder.build());

		return buildSessionFactory;
	}

	private static void out(String string) {
		System.out.println(string);
	}
}
