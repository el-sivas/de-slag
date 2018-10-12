package de.slag.finance;

import java.util.function.Supplier;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmountFactory;
import javax.money.MonetaryContext;
import javax.money.NumberValue;

import org.javamoney.moneta.spi.DefaultNumberValue;

public class MonetaryAmountFactorySupplier implements Supplier<MonetaryAmountFactory<MonetaryAmount>> {

	private static MonetaryAmountFactorySupplier instance;

	public static MonetaryAmountFactory<MonetaryAmount> creating() {
		if (instance == null) {
			instance = new MonetaryAmountFactorySupplier();
		}
		return instance.get();
	}

	@Override
	public MonetaryAmountFactory<MonetaryAmount> get() {
		return new MonetaryAmountFactory<MonetaryAmount>() {

			private CurrencyUnit currency;

			private Number number;

			private MonetaryContext context;

			@Override
			public Class<? extends javax.money.MonetaryAmount> getAmountType() {
				return MonetaryAmount.class;
			}

			@Override
			public MonetaryAmountFactory<MonetaryAmount> setCurrency(CurrencyUnit currency) {
				this.currency = currency;
				return this;
			}

			@Override
			public MonetaryAmountFactory<MonetaryAmount> setNumber(double number) {
				this.number = number;
				return this;
			}

			@Override
			public MonetaryAmountFactory<MonetaryAmount> setNumber(long number) {
				this.number = number;
				return this;
			}

			@Override
			public MonetaryAmountFactory<MonetaryAmount> setNumber(Number number) {
				this.number = number;
				return this;
			}

			@Override
			public NumberValue getMaxNumber() {
				return new DefaultNumberValue(Integer.MAX_VALUE);
			}

			@Override
			public NumberValue getMinNumber() {
				return new DefaultNumberValue(Integer.MIN_VALUE);
			}

			@Override
			public MonetaryAmountFactory<MonetaryAmount> setContext(MonetaryContext monetaryContext) {
				this.context = monetaryContext;
				return this;
			}

			@Override
			public MonetaryAmount create() {
				return new MonetaryAmount(new DefaultNumberValue(number), currency);
			}

			@Override
			public MonetaryContext getDefaultMonetaryContext() {
				return context;
			}
		};
	}

}
