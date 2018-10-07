package de.slag.central.data.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.slag.central.data.config.DawnConfig;
import de.slag.central.data.config.DawnFileConfig;
import de.slag.central.data.database.DatabaseConnectionProperties;
import de.slag.central.data.database.DawnHibernateSupport;

public abstract class AbstractDao {

	protected SessionFactory getSessionFactory() {
		final DawnConfig config = DawnFileConfig.instance();
		final DatabaseConnectionProperties creating = DatabaseConnectionProperties.creating(config);
		final DawnHibernateSupport instance = DawnHibernateSupport.getInstance();
		final SessionFactory sessionFactory = instance.getSessionFactory(creating);
		return sessionFactory;
	}
	
	protected Session openSession() {
		return getSessionFactory().openSession();
	}

}
