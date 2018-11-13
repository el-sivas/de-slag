package de.slag.central.logic.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

import de.slag.central.ApplicationException;
import de.slag.central.data.ApplicationBeanDao;
import de.slag.central.model.ApplicationBean;

public abstract class AbstractApplicationBeanService<AB extends ApplicationBean> {

	protected abstract Class<AB> getBeanClass();

	protected abstract ApplicationBeanDao<AB> getDao();

	protected Supplier<AB> getSupplier() {
		return () -> {
			try {
				return getBeanClass().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new LogicException(e);
			}
		};
	}

	protected AB createInternal() {
		try {
			return getBeanClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new LogicException(e);
		}
	}

	public AB loadBy(long id) {
		final Optional<AB> loadBy = getDao().loadBy(id, getSupplier());
		if (loadBy.isPresent()) {
			return loadBy.get();
		}
		throw new ApplicationException("no data found with id: " + id + ", class: " + getBeanClass().getName());
	}

	public void save(AB bean) {
		final ApplicationBeanDao<AB> dao = getDao();
		dao.save(bean);
	}

	public void delete(AB bean) {
		getDao().delete(bean);
	}

	public void delete(Long id) {
		getDao().deleteBy(id);
	}

	public Collection<AB> findAll() {
		return getDao().findAll(getSupplier());
	}

	public void saveAll(Collection<AB> beans) {
		beans.forEach(e -> save(e));
	}

}
