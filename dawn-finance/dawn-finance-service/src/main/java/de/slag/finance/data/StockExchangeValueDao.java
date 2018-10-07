package de.slag.finance.data;

import java.util.function.Supplier;

import de.slag.central.data.ApplicationBeanDao;
import de.slag.finance.model.StockExchangeValue;

public interface StockExchangeValueDao extends ApplicationBeanDao<StockExchangeValue> {

	static StockExchangeValueDao fake() {
		return new StockExchangeValueDao() {
			
			@Override
			public void save(StockExchangeValue bean) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public StockExchangeValue loadBy(long id, Supplier<StockExchangeValue> beanSupplier) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void delete(StockExchangeValue bean) {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
}
