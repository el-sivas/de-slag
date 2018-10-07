package de.slag.central.data.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class PersistBean {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column
	private Date validUntil;

	@Column
	private Date createdAt;
	
	@Column
	private Date lastUpdate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
