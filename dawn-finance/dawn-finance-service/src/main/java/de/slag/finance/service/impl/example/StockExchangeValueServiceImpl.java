package de.slag.finance.service.impl.example;

import java.util.Optional;

import de.slag.central.data.ApplicationBeanDao;
import de.slag.central.logic.impl.AbstractApplicationBeanService;
import de.slag.central.service.ApplicationBeanCredentials;
import de.slag.finance.data.StockExchangeValueDao;
import de.slag.finance.model.StockExchangeValue;
import de.slag.finance.service.StockExchangeValueService;

public class StockExchangeValueServiceImpl extends AbstractApplicationBeanService<StockExchangeValue>
		implements StockExchangeValueService {
	
	private StockExchangeValueDao stockExchangeValueDao = StockExchangeValueDao.fake();

	@Override
	public StockExchangeValue create(Optional<ApplicationBeanCredentials<StockExchangeValue>> credentials) {
		return createInternal();
	}

	@Override
	protected Class<StockExchangeValue> getBeanClass() {
		return StockExchangeValue.class;
	}

	@Override
	protected ApplicationBeanDao<StockExchangeValue> getDao() {
		return stockExchangeValueDao;
	}

}
