package de.slag.finance.data.impl;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.slag.central.data.impl.PersistBean;

@Entity
@Table(name = "se_value")
public class PersistSeValue extends PersistBean {

	@Column
	private String isin;

	@Column
	private String name;
	
	@Basic
	private Boolean testdaten;

}
