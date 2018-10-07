package de.slag.central.data;

import java.util.Collection;
import java.util.function.Supplier;

import de.slag.central.model.ApplicationBean;

public interface ApplicationBeanDao<AB extends ApplicationBean> {

	AB loadBy(long id, Supplier<AB> beanSupplier);

	void save(AB bean);

	void delete(AB bean);
	
	void delete(Long id);
	
	Collection<AB> findAll(Supplier<AB> beanSupplier);

}
