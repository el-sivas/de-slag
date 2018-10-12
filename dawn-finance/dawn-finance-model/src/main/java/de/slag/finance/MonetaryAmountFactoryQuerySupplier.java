package de.slag.finance;

import java.util.function.Supplier;

import javax.money.MonetaryAmountFactoryQuery;
import javax.money.MonetaryAmountFactoryQueryBuilder;

public class MonetaryAmountFactoryQuerySupplier implements Supplier<MonetaryAmountFactoryQuery> {

	private static MonetaryAmountFactoryQuerySupplier instance;

	public static MonetaryAmountFactoryQuery creating() {
		if (instance == null) {
			instance = new MonetaryAmountFactoryQuerySupplier();
		}
		return instance.get();
	}

	@Override
	public MonetaryAmountFactoryQuery get() {
		final MonetaryAmountFactoryQueryBuilder of = MonetaryAmountFactoryQueryBuilder.of();
		return of.build();
	}

}
