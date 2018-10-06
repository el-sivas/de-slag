package de.slag.central.data.database;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import de.slag.base.tools.ClassUtils;
import de.slag.central.data.impl.PersistBean;

public class DawnHibernateSupport {

	private static DawnHibernateSupport instance;

	private Map<DatabaseConnectionProperties, SessionFactory> con = new HashMap<>();

	private DawnHibernateSupport() {
	}

	public static DawnHibernateSupport getInstance() {
		if (instance == null) {
			instance = new DawnHibernateSupport();
		}
		return instance;
	}
	
	public boolean isValidDatabase(DatabaseConnectionProperties properties) {
		final Configuration configuration = configuration(properties);
		configuration.setProperty("hibernate.hbm2ddl.auto", "validate");
		findAnnotatedClasses().forEach(c -> configuration.addAnnotatedClass(c));
		createSessionFactory(configuration);
		return true;
	}
	
	public void updateDatabase(DatabaseConnectionProperties properties) {
		final Configuration configuration = configuration(properties);
		configuration.setProperty("hibernate.hbm2ddl.auto", "update");
		findAnnotatedClasses().forEach(c -> configuration.addAnnotatedClass(c));
		createSessionFactory(configuration);
	}
	
	public SessionFactory getSessionFactory(DatabaseConnectionProperties properties) {
		if (!con.containsKey(properties)) {
			con.put(properties, createSessionFactory(properties));
		}
		return con.get(properties);
	}

	private SessionFactory createSessionFactory(DatabaseConnectionProperties properties) {
		final Configuration configuration = configuration(properties);
		findAnnotatedClasses().forEach(c -> configuration.addAnnotatedClass(c));
		return createSessionFactory(configuration);
	}

	private Configuration configuration(DatabaseConnectionProperties properties) {
		String driverClass = properties.getDbDriver();
		String url = properties.getDbUrl();
		String username = properties.getDbUser();
		String password = properties.getDbPassword();
		String dialect = properties.getDbDialect();
		return configuration(driverClass, url, username, password, dialect);
	}


	private Configuration configuration(String driverClass, String url, String username, String password,
			String dialect) {
		
		final Configuration configuration = new Configuration();
		configuration.setProperty("hibernate.connection.driver_class", driverClass);
		configuration.setProperty("hibernate.connection.url", url);
		configuration.setProperty("hibernate.connection.username", username);
		configuration.setProperty("hibernate.connection.password", password);
		configuration.setProperty("hibernate.dialect", dialect);
		configuration.setProperty("hibernate.show_sql", "true");
		configuration.setProperty("hibernate.connection.pool_size", "10");
		return configuration;
	}

	private static SessionFactory createSessionFactory(final Configuration configuration) {
		return configuration.buildSessionFactory(
				new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());
	}

	private static Collection<Class> findAnnotatedClasses() {
		return ClassUtils.getAllSubclassesOf(PersistBean.class);
	}
}
