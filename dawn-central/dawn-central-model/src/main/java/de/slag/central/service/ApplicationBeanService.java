package de.slag.central.service;

import java.util.Collection;
import java.util.Optional;

import de.slag.central.model.ApplicationBean;

public interface ApplicationBeanService<AB extends ApplicationBean> {

	AB loadBy(long id);

	void save(AB bean);

	void delete(AB bean);
	
	void delete(Long id);
	
	Collection<AB> findAll();

	default AB create() {
		return create(Optional.empty());
	}

	default AB create(ApplicationBeanCredentials<AB> credentials) {
		return create(Optional.of(credentials));
	}
	
	AB create(Optional<ApplicationBeanCredentials<AB>> credentials);
	
	

}
