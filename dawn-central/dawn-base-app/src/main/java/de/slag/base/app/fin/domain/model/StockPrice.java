package de.slag.base.app.fin.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;

import de.slag.base.tools.DateUtils;

public class StockPrice {

	private BigDecimal price;

	private String currency;

	private LocalDate date;

	private String isin;

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public static StockPrice creating(double price, String currency, Date date, String isin) {
		final StockPrice stockPrice = new StockPrice();
		stockPrice.setPrice(BigDecimal.valueOf(price));
		stockPrice.setCurrency(currency);
		stockPrice.setIsin(isin);
		stockPrice.setDate(DateUtils.toLocalDate(date));
		return stockPrice;
	}

	public static Comparator<StockPrice> comparing() {
		return new Comparator<StockPrice>() {

			@Override
			public int compare(StockPrice o1, StockPrice o2) {
				final int compareTo = o1.getDate().compareTo(o2.getDate());
				if (compareTo != 0) {
					return compareTo;
				}
				return o1.getIsin().compareTo(o2.getIsin());
			}
		};
	}
}
