package de.slag.central.model;

import java.time.LocalDate;

public interface ApplicationBean {

	Long getId();

	LocalDate getCreated();

	boolean isDeleted();

	/**
	 * @return A human readable label of this bean.
	 */
	String getLabel();

}
