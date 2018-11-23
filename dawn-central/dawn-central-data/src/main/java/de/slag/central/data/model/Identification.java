package de.slag.central.data.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

import de.slag.central.data.impl.PersistBean;

@Entity
public class Identification extends PersistBean {
	
	private static final String FIRST_NAME = "FIRST_NAME";
	
	private static final String LAST_NAME = "LAST_NAME";

	@Column(unique = true)
	private String identifier;

	@Column
	private String idHash;

	@Basic
	private Boolean active;
	
	@Column
	private Date lastLogin;
	
	@Basic
	private Integer failedLogins;

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
	
	public String getFirstName() {
		return getElseString(FIRST_NAME);
	}
	
	public void setFirstName(String firstName) {
		setElseString(FIRST_NAME, firstName);
	}
	
	public String getLastName() {
		return getElseString(LAST_NAME);
	}
	
	public void setLastName(String lastName) {
		setElseString(LAST_NAME, lastName);
	}


}
