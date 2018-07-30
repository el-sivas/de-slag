package de.slag.central.data;

import java.util.Collection;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class SlagHibernateSupportUtils {

	private static final String HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String HIBERNATE_CONNECTION_PASSWORD = "hibernate.connection.password";
	private static final String HIBERNATE_CONNECTION_USERNAME = "hibernate.connection.username";
	private static final String HIBERNATE_CONNECTION_URL = "hibernate.connection.url";
	private static final String HIBERNATE_CONNECTION_DRIVER_CLASS = "hibernate.connection.driver_class";

	public static boolean isDatabaseValid() {
		getSessionFactory(true);
		return true;
	}

	public SessionFactory getSessionFactory() {
		return getSessionFactory(false);
	}

	private static SessionFactory getSessionFactory(boolean validate) {
		final DawnConfig dawnConfig = DawnFileConfig.instance();

		Configuration configuration = new Configuration();

		findAnnotatedClasses().forEach(c -> configuration.addAnnotatedClass(c));
		configuration.setProperty(HIBERNATE_CONNECTION_DRIVER_CLASS,
				dawnConfig.getStringValue(HIBERNATE_CONNECTION_DRIVER_CLASS));

		configuration.setProperty(HIBERNATE_CONNECTION_URL, dawnConfig.getStringValue(HIBERNATE_CONNECTION_URL));

		configuration.setProperty(HIBERNATE_CONNECTION_USERNAME,
				dawnConfig.getStringValue(HIBERNATE_CONNECTION_USERNAME));

		configuration.setProperty(HIBERNATE_CONNECTION_PASSWORD,
				dawnConfig.getStringValue(HIBERNATE_CONNECTION_PASSWORD));

		configuration.setProperty(HIBERNATE_DIALECT, dawnConfig.getStringValue(HIBERNATE_DIALECT));

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
		hier alle ableitungen von PersistBean.class durchscannen
	}

}
