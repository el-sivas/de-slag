package de.slag.central.data.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import de.slag.base.tools.FieldMappingUtils;
import de.slag.central.model.ApplicationBean;
import de.slag.central.model.adm.User;

public abstract class AbstractApplicationBeanDao<PB extends PersistBean, AB extends ApplicationBean>
		extends AbstractPersistBeanDao<PB> {

	public AB loadBy(long id, Supplier<AB> beanSupplier) {
		final PB persistBean = loadById(id);
		final AB bean = beanSupplier.get();
		mapPersistToApplication().accept(persistBean, bean);
		return bean;
	}

	protected BiConsumer<PB, AB> mapPersistToApplication() {
		return new BiConsumer<PB, AB>() {

			@Override
			public void accept(PB t, AB u) {

				// hier wird alles gemappt
				FieldMappingUtils.map(t, u, Collections.emptyList(), true);
			}
		};
	}

	protected BiConsumer<AB, PB> mapApplicationToPersist() {
		return new BiConsumer<AB, PB>() {

			@Override
			public void accept(AB t, PB u) {

				// hier wird alles gemappt außer die 'id'
				FieldMappingUtils.map(t, u, Arrays.asList("id"), true);
			}
		};
	}

	public void save(AB bean) {
		final Long id = bean.getId();

		final PB persistBean;
		if (id != null) {
			persistBean = loadById(id);
		} else {
			persistBean = create();
		}
		mapApplicationToPersist().accept(bean, persistBean);
		save(persistBean);
	}

	public void delete(AB bean) {
		final PB persistBean = loadById(bean.getId());
		delete(persistBean);
		mapPersistToApplication().accept(persistBean, bean);
	}

	public void delete(Long id) {
		final PB loadById = loadById(id);
		if (loadById == null) {
			return;
		}
		delete(loadById);
	}

	public Collection<AB> findAll(Supplier<AB> beanSupplier) {
		final Collection<PB> persistBeans = findAll();
		final Collection<AB> beans = new ArrayList<>();
		for (PB pb : persistBeans) {
			final AB ab = beanSupplier.get();
			mapPersistToApplication().accept(pb, ab);
			beans.add(ab);
		}
		return beans;
	}
}
