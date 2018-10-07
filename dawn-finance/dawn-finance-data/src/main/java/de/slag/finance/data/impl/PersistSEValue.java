package de.slag.finance.data.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.slag.central.data.impl.PersistBean;

@Entity
@Table(name = "se_value")
public class PersistSEValue extends PersistBean {

	@Column
	private String isin;

	@Column
	private String name;

}
