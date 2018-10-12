package de.slag.finance;

import java.util.function.Supplier;

import javax.money.MonetaryContext;

public class MonetaryContextSupplier implements Supplier<MonetaryContext> {

	public static MonetaryContext creating() {
		final MonetaryContextSupplier monetaryContextSupplier = new MonetaryContextSupplier();
		return monetaryContextSupplier.get();
	}

	@Override
	public MonetaryContext get() {
		return MonetaryContext.from(MonetaryAmountFactoryQuerySupplier.creating(), MonetaryAmount.class);
	}

}
