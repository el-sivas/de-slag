package de.slag.central.persist;

import java.util.Optional;

import de.slag.central.model.ApplicationBean;

public interface ApplicationBeanDao<T extends ApplicationBean> {

	Optional<T> loadById(long id);

	void delete(T bean);
	
	void save(T bean);

}
