package de.slag.central.data.database;

import org.springframework.stereotype.Service;

import de.slag.central.data.config.DawnFileConfig;
import de.slag.central.service.adm.DawnHibernateService;

@Service
public class DawnHibernateServiceImpl implements DawnHibernateService {

	@Override
	public boolean isValidDatabase() {
		return DawnHibernateSupport.getInstance().isValidDatabase(databaseConnectionProperties());
	}

	@Override
	public void updateDatabase() {
		DawnHibernateSupport.getInstance().updateDatabase(databaseConnectionProperties());
	}

	public DatabaseConnectionProperties databaseConnectionProperties() {
			return DatabaseConnectionProperties.creating(DawnFileConfig.instance());
	}

}
