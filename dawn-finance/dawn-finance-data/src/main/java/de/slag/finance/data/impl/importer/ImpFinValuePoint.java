package de.slag.finance.data.impl.importer;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.slag.central.data.impl.PersistBean;

@Entity
@Table(name="IMP_FIN_VALUE_POINT")
public class ImpFinValuePoint extends PersistBean {
	
	@Column
	private String isin;
	
	@Column
	private BigDecimal price;
	
	@Column
	private Date date;
	
	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


}
