package de.slag.finance.data.impl;

import org.springframework.stereotype.Repository;

import de.slag.central.data.impl.AbstractApplicationBeanDao;
import de.slag.finance.data.CurrencyPriceDao;
import de.slag.finance.model.CurrencyPrice;

@Repository
public class CurrencyPriceDaoImpl extends AbstractApplicationBeanDao<PersistCurrencyPrice, CurrencyPrice>
		implements CurrencyPriceDao {

	@Override
	protected Class<PersistCurrencyPrice> getBeanClass() {
		return PersistCurrencyPrice.class;
	}
}
