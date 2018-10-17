package de.slag.finance;

import java.math.BigDecimal;

import javax.money.MonetaryAmountFactory;

public class DawnFinanceMonetaryAmountFactorySupport {

	public static MonetaryAmountFactory<MonetaryAmount> getMonetaryAmountFactory() {
		return MonetaryAmountFactorySupplier.creating();
	}

	public static MonetaryAmount create(BigDecimal value) {
		return create(value, "EUR");
	}

	public static MonetaryAmount create(BigDecimal value, String currency) {
		MonetaryAmountFactory<MonetaryAmount> monetaryAmountFactory = getMonetaryAmountFactory();
		monetaryAmountFactory.setNumber(value.doubleValue());
		monetaryAmountFactory.setCurrency(currency);
		return monetaryAmountFactory.create();
	}

}
