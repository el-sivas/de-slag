package de.slag.finance;

import java.math.BigDecimal;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmountFactory;
import javax.money.MonetaryContext;
import javax.money.NumberValue;
import javax.naming.OperationNotSupportedException;

import de.slag.central.ApplicationException;

public class MonetaryAmount implements javax.money.MonetaryAmount {

	private CurrencyUnit currencyUnit;

	private NumberValue numberValue;

	public MonetaryAmount(NumberValue value, CurrencyUnit currency) {
		this.numberValue = value;
		this.currencyUnit = currency;
	}

	@Override
	public CurrencyUnit getCurrency() {
		return currencyUnit;
	}

	@Override
	public NumberValue getNumber() {
		return numberValue;
	}

	@Override
	public int compareTo(javax.money.MonetaryAmount o) {
		Utils.assertEqualCurrency(o, this);
		return this.numberValue.compareTo(o.getNumber());
	}

	@Override
	public MonetaryContext getMonetaryContext() {
		return MonetaryContextSupplier.creating();
	}

	@Override
	public MonetaryAmountFactory<? extends javax.money.MonetaryAmount> getFactory() {
		return MonetaryAmountFactorySupplier.creating();
	}

	@Override
	public boolean isGreaterThan(javax.money.MonetaryAmount amount) {
		return compareTo(amount) > 0;
	}

	@Override
	public boolean isGreaterThanOrEqualTo(javax.money.MonetaryAmount amount) {
		return compareTo(amount) > 0;
	}

	@Override
	public boolean isLessThan(javax.money.MonetaryAmount amount) {
		return compareTo(amount) < 0;
	}

	@Override
	public boolean isLessThanOrEqualTo(javax.money.MonetaryAmount amount) {
		return compareTo(amount) < 0;
	}

	@Override
	public boolean isEqualTo(javax.money.MonetaryAmount amount) {
		return compareTo(amount) == 0;
	}

	@Override
	public int signum() {
		final double doubleValue = numberValue.doubleValue();
		if (doubleValue > 1) {
			return 1;
		}
		if (doubleValue < 1) {
			return -1;
		}
		return 0;

	}

	@Override
	public javax.money.MonetaryAmount add(javax.money.MonetaryAmount amount) {
		Utils.assertEqualCurrency(this, amount);
		final BigDecimal one = BigDecimal.valueOf(numberValue.doubleValue());
		final BigDecimal other = BigDecimal.valueOf(amount.getNumber().doubleValue());

		final BigDecimal value = one.add(other);

		return create(value);
	}

	@Override
	public javax.money.MonetaryAmount subtract(javax.money.MonetaryAmount amount) {
		Utils.assertEqualCurrency(this, amount);
		final BigDecimal one = BigDecimal.valueOf(numberValue.doubleValue());
		final BigDecimal other = BigDecimal.valueOf(amount.getNumber().doubleValue());

		final BigDecimal value = one.subtract(other);

		return create(value);
	}

	@Override
	public javax.money.MonetaryAmount multiply(long multiplicand) {
		return multiply(Double.valueOf(String.valueOf(multiplicand)).doubleValue());
	}

	@Override
	public javax.money.MonetaryAmount multiply(double multiplicand) {
		final BigDecimal one = BigDecimal.valueOf(numberValue.doubleValue());
		final BigDecimal other = BigDecimal.valueOf(multiplicand);

		final BigDecimal value = one.multiply(other);

		return create(value);
	}

	@Override
	public javax.money.MonetaryAmount multiply(Number multiplicand) {
		return multiply(multiplicand.doubleValue());
	}

	@Override
	public javax.money.MonetaryAmount divide(long divisor) {
		return divide(Double.valueOf(String.valueOf(divisor)).doubleValue());
	}

	@Override
	public javax.money.MonetaryAmount divide(double divisor) {
		final BigDecimal one = BigDecimal.valueOf(numberValue.doubleValue());
		final BigDecimal other = BigDecimal.valueOf(divisor);

		final BigDecimal value = one.divide(other);

		return create(value);
	}

	@Override
	public javax.money.MonetaryAmount divide(Number divisor) {
		return divide(divisor.doubleValue());
	}

	@Override
	public javax.money.MonetaryAmount remainder(long divisor) {
		throw new ApplicationException(new OperationNotSupportedException());
	}

	@Override
	public javax.money.MonetaryAmount remainder(double divisor) {
		throw new ApplicationException(new OperationNotSupportedException());
	}

	@Override
	public javax.money.MonetaryAmount remainder(Number divisor) {
		throw new ApplicationException(new OperationNotSupportedException());
	}

	@Override
	public javax.money.MonetaryAmount[] divideAndRemainder(long divisor) {
		throw new ApplicationException(new OperationNotSupportedException());
	}

	@Override
	public javax.money.MonetaryAmount[] divideAndRemainder(double divisor) {
		throw new ApplicationException(new OperationNotSupportedException());
	}

	@Override
	public javax.money.MonetaryAmount[] divideAndRemainder(Number divisor) {
		throw new ApplicationException(new OperationNotSupportedException());
	}

	@Override
	public javax.money.MonetaryAmount divideToIntegralValue(long divisor) {
		throw new ApplicationException(new OperationNotSupportedException());
	}

	@Override
	public javax.money.MonetaryAmount divideToIntegralValue(double divisor) {
		throw new ApplicationException(new OperationNotSupportedException());
	}

	@Override
	public javax.money.MonetaryAmount divideToIntegralValue(Number divisor) {
		throw new ApplicationException(new OperationNotSupportedException());
	}

	@Override
	public javax.money.MonetaryAmount scaleByPowerOfTen(int power) {
		throw new ApplicationException(new OperationNotSupportedException());
	}

	@Override
	public javax.money.MonetaryAmount abs() {
		return create(Utils.of(this).abs());
	}

	@Override
	public javax.money.MonetaryAmount negate() {
		return create(Utils.of(this).negate());
	}

	@Override
	public javax.money.MonetaryAmount plus() {
		return create(Utils.of(this).plus());
	}

	@Override
	public javax.money.MonetaryAmount stripTrailingZeros() {
		throw new ApplicationException(new OperationNotSupportedException());
	}

	private javax.money.MonetaryAmount create(BigDecimal value) {
		return Utils.create(getFactory(), value, currencyUnit);
	}

	private static class Utils {

		public static javax.money.MonetaryAmount create(
				MonetaryAmountFactory<? extends javax.money.MonetaryAmount> factory, BigDecimal value,
				CurrencyUnit currencyUnit) {
			factory.setNumber(value.doubleValue());
			factory.setCurrency(currencyUnit);
			return factory.create();

		}

		public static BigDecimal of(javax.money.MonetaryAmount amount) {
			return BigDecimal.valueOf(amount.getNumber().doubleValue());
		}

		public static void assertEqualCurrency(javax.money.MonetaryAmount one, javax.money.MonetaryAmount two) {
			if (one.getCurrency().equals(two.getCurrency())) {
				return;
			}
			throw new ApplicationException(new OperationNotSupportedException("different currencies"));
		}
	}

}
