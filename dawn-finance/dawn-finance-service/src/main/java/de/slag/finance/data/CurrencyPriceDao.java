package de.slag.finance.data;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Supplier;

import de.slag.central.data.ApplicationBeanDao;
import de.slag.finance.model.CurrencyPrice;

public interface CurrencyPriceDao extends ApplicationBeanDao<CurrencyPrice> {

	static CurrencyPriceDao fake() {
		return new CurrencyPriceDao() {

			@Override
			public void save(CurrencyPrice bean) {
				// TODO Auto-generated method stub

			}

			@Override
			public Optional<CurrencyPrice> loadBy(long id, Supplier<CurrencyPrice> beanSupplier) {
				return Optional.empty();
			}

			@Override
			public Collection<CurrencyPrice> findAll(Supplier<CurrencyPrice> beanSupplier) {
				return Collections.emptyList();
			}

			@Override
			public void deleteBy(Long id) {
				// TODO Auto-generated method stub

			}

			@Override
			public void delete(CurrencyPrice bean) {
				// TODO Auto-generated method stub

			}
		};
	}

}
