package de.slag.finance.service;

import java.util.Collection;

import de.slag.central.service.ApplicationBeanService;
import de.slag.finance.model.SeValuePricePoint;

public interface SeValuePricePointService extends ApplicationBeanService<SeValuePricePoint> {
	
	public void importFromStaging(Collection<String> isins);

}
