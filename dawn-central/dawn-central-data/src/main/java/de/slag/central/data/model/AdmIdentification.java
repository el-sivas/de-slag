package de.slag.central.data.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

import de.slag.central.data.impl.PersistBean;

@Entity
public class AdmIdentification extends PersistBean {

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

}
