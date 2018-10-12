package de.slag.finance.model;

import java.util.Date;

import javax.money.MonetaryAmount;

import de.slag.base.Labelable;
import de.slag.central.model.ApplicationBean;

public class SeValuePricePoint extends ApplicationBean implements Labelable  {

	private SeValue seValue;

	private MonetaryAmount price;

	private Date priceDate;

	public SeValue getSeValue() {
		return seValue;
	}

	public void setSeValue(SeValue seValue) {
		this.seValue = seValue;
	}

	public MonetaryAmount getPrice() {
		return price;
	}

	public void setPrice(MonetaryAmount price) {
		this.price = price;
	}

	public Date getPriceDate() {
		return priceDate;
	}

	public void setPriceDate(Date priceDate) {
		this.priceDate = priceDate;
	}

	@Override
	public String getLabel() {
		return toString();
	}

}
