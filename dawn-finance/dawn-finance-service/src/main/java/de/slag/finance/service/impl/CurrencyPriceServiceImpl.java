package de.slag.finance.service.impl;

import java.util.Currency;
import java.util.Optional;

import javax.annotation.Resource;

import de.slag.central.data.ApplicationBeanDao;
import de.slag.central.logic.impl.AbstractApplicationBeanService;
import de.slag.central.service.ApplicationBeanCredentials;
import de.slag.finance.data.CurrencyPriceDao;
import de.slag.finance.model.CurrencyPrice;
import de.slag.finance.service.CurrencyPriceService;

public class CurrencyPriceServiceImpl extends AbstractApplicationBeanService<CurrencyPrice> implements CurrencyPriceService {
	
	private static final Currency DEFAULT_CURRENCY = Currency.getInstance("EUR");

	@Resource
	private CurrencyPriceDao currencyPriceDao;
	
	@Override
	public CurrencyPrice create(Optional<ApplicationBeanCredentials<CurrencyPrice>> credentials) {
		return new CurrencyPrice(DEFAULT_CURRENCY);
	}

	@Override
	protected Class<CurrencyPrice> getBeanClass() {
		return CurrencyPrice.class;
	}

	@Override
	protected ApplicationBeanDao<CurrencyPrice> getDao() {
		return currencyPriceDao;
	}

	

}
