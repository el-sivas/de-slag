package de.slag.central.data.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import de.slag.central.DawnConstants;

@MappedSuperclass
public abstract class PersistBean {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column
	private Date validUntil = DawnConstants.END_OF_DEKAMILLENIAL;

	@Column
	private Date createdAt = new Date();

	@Column
	private Date lastUpdate;

	public Long getId() {
		return id;
	}
}
