package de.slag.finance.service;

import java.util.Optional;

import de.slag.central.service.ApplicationBeanService;
import de.slag.finance.model.SeValue;

public interface SeValueService extends ApplicationBeanService<SeValue> {
	
	Optional<SeValue> loadByIsin(String isin);

	

}
