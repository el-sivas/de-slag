package de.slag.central.data.impl;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Identification extends PersistBean {

	@Column(unique = true)
	private String identifier;

	@Column
	private String idHash;

	@Basic
	private Boolean active;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getIdHash() {
		return idHash;
	}

	public void setIdHash(String idHash) {
		this.idHash = idHash;
	}

}
