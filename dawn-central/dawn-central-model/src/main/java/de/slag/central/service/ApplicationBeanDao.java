package de.slag.central.service;

import java.util.Optional;

import de.slag.central.model.ApplicationBean;

public interface ApplicationBeanDao<AB extends ApplicationBean> {

	Optional<AB> loadById(long id);

	void delete(AB bean);
	
	void save(AB bean);

}
