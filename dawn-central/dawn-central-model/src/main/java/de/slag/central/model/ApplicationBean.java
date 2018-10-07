package de.slag.central.model;

import java.util.Date;

import de.slag.central.DawnConstants;

public abstract class ApplicationBean {

	private Long id;

	private Date createdAt = new Date();

	private Date validUntil = DawnConstants.END_OF_DEKAMILLENIAL;

	public Long getId() {
		return id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public boolean isDeleted() {
		return validUntil.before(new Date());
	}
}
