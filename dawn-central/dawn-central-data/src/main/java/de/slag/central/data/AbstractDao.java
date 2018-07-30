package de.slag.central.data;

import org.hibernate.SessionFactory;

import de.slag.central.data.config.DawnConfig;
import de.slag.central.data.config.DawnFileConfig;
import de.slag.central.data.database.DawnHibernateSupport;

public abstract class AbstractDao<PB extends PersistBean> {
	
	protected SessionFactory getSessionFactory() {
		final DawnConfig config = DawnFileConfig.instance();
		return null;
		
				
	}
}
