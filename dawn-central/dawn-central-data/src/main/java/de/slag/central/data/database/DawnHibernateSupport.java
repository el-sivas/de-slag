package de.slag.central.data.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import de.slag.base.tools.ClassUtils;
import de.slag.central.data.PersistBean;
import de.slag.central.data.config.DawnConfig;
import de.slag.central.data.config.DawnFileConfig;

public class DawnHibernateSupport {

	private static DawnHibernateSupport instance;

	private Collection<DatabaseConnectionProperties> connections = new ArrayList<>();

	private Map<DatabaseConnectionProperties, SessionFactory> con = new HashMap<>();

	private DawnHibernateSupport() {
	}

	public static DawnHibernateSupport getInstance() {
		if (instance == null) {
			instance = new DawnHibernateSupport();
		}
		return instance;
	}

	public static boolean isDatabaseValid() {
		getSessionFactory(true);
		return true;
	}

	public SessionFactory getSessionFactory() {
		return getSessionFactory(false);
	}

	public SessionFactory getSessionFactory(DatabaseConnectionProperties properties) {
		if (!con.containsKey(properties)) {
			return con.put(properties, createSessionFactory(properties));
		}
		return con.get(properties);
	}

	private SessionFactory createSessionFactory(DatabaseConnectionProperties properties) {
		return null;
	}

	private static SessionFactory getSessionFactory(boolean validate) {
		final DawnConfig dawnConfig = DawnFileConfig.instance();

		String driverClass = dawnConfig.getStringValue(DawnConfig.HIBERNATE_CONNECTION_DRIVER_CLASS);
		String url = dawnConfig.getStringValue(DawnConfig.HIBERNATE_CONNECTION_URL);
		String username = dawnConfig.getStringValue(DawnConfig.HIBERNATE_CONNECTION_USERNAME);
		String password = dawnConfig.getStringValue(DawnConfig.HIBERNATE_CONNECTION_PASSWORD);
		String dialect = dawnConfig.getStringValue(DawnConfig.HIBERNATE_DIALECT);

		return createSessionFactory(validate, driverClass, url, username, password, dialect);
	}

	private static SessionFactory createSessionFactory(boolean validate, String driverClass, String url,
			String username, String password, String dialect) {
		Configuration configuration = new Configuration();
		findAnnotatedClasses().forEach(c -> configuration.addAnnotatedClass(c));
		configuration.setProperty(DawnConfig.HIBERNATE_CONNECTION_DRIVER_CLASS, driverClass);

		configuration.setProperty(DawnConfig.HIBERNATE_CONNECTION_URL, url);

		configuration.setProperty(DawnConfig.HIBERNATE_CONNECTION_USERNAME, username);

		configuration.setProperty(DawnConfig.HIBERNATE_CONNECTION_PASSWORD, password);

		configuration.setProperty(DawnConfig.HIBERNATE_DIALECT, dialect);

		configuration.setProperty("hibernate.show_sql", "true");
		configuration.setProperty("hibernate.connection.pool_size", "10");

		if (validate) {
			configuration.setProperty("hibernate.hbm2ddl.auto", "validate");
		} else {
			configuration.setProperty("hibernate.hbm2ddl.auto", "update");
		}

		// out("build session factory. validate: " + validate);
		return configuration.buildSessionFactory(
				new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());
	}

	private static Collection<Class> findAnnotatedClasses() {
		return ClassUtils.getAllSubclassesOf(PersistBean.class);
	}
}
