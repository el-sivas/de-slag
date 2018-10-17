package de.slag.finance.data.impl.dev;

import java.math.BigDecimal;
import java.util.Date;

import javax.money.MonetaryAmount;
import javax.money.MonetaryAmountFactory;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.slag.central.data.impl.PersistBean;
import de.slag.finance.MonetaryAmountFactorySupplier;
import de.slag.finance.data.impl.FinSeValue;

//@Entity
@Table(name = "se_value_point")
public class PersistSeValuePoint extends PersistBean {

	@ManyToOne
	private FinSeValue seValue;
	
	@Column
	private BigDecimal price;
	
	@Column
	private String priceCur;
	
	@Column
	private Date priceDate;
	
	public void setPrice(MonetaryAmount amount) {
		price = BigDecimal.valueOf(amount.getNumber().doubleValue());
		priceCur = amount.getCurrency().getCurrencyCode();
	}
	
	public MonetaryAmount getPrice() {
		MonetaryAmountFactory<de.slag.finance.MonetaryAmount> factory = MonetaryAmountFactorySupplier.creating();
		factory.setNumber(price.doubleValue());
		factory.setCurrency(priceCur);
		return factory.create();
	}
	
	

}
