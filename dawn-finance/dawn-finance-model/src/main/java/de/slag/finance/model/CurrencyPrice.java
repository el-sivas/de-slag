package de.slag.finance.model;

import java.math.BigDecimal;
import java.util.Currency;

import de.slag.central.model.ApplicationBean;

public class CurrencyPrice extends ApplicationBean {
	
	private final BigDecimal price;
	
	private final Currency currency;
	
	public CurrencyPrice(Currency currency) {
		this(BigDecimal.ZERO, currency);
	}
	
	public CurrencyPrice(BigDecimal price, Currency currency) {
		super();
		this.price = price;
		this.currency = currency;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Currency getCurrency() {
		return currency;
	}
}
