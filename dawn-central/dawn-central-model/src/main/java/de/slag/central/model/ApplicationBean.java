package de.slag.central.model;

import java.time.LocalDateTime;
import java.util.Date;

import de.slag.base.tools.DateUtils;

public abstract class ApplicationBean {

	private static final Date END_OF_DEKAMILLENIAL = DateUtils.toDate(LocalDateTime.of(9999, 12, 31, 23, 59, 59));

	private Long id;

	private Date createdAt = new Date();

	private Date validUntil = END_OF_DEKAMILLENIAL;

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
