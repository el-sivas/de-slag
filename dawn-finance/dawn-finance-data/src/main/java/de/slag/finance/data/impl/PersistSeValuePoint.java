package de.slag.finance.data.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.slag.central.data.impl.PersistBean;

@Entity
@Table(name = "se_value_point")
public class PersistSeValuePoint extends PersistBean {

	@ManyToOne
	private PersistSeValue seValue;
	
	@Column
	private BigDecimal price;	
	
	@Column
	private Date priceDate;

}
