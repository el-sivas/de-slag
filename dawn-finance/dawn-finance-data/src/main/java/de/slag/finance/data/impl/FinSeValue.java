package de.slag.finance.data.impl;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

import de.slag.central.data.impl.PersistBean;

@Entity
public class FinSeValue extends PersistBean {

	@Column
	private String isin;

	@Column
	private String name;
	
	@Basic
	private Boolean testdaten;

}
