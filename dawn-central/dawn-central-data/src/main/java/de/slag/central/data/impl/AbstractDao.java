package de.slag.central.data.impl;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import de.slag.base.tools.FieldMappingUtils;
import de.slag.central.data.config.DawnConfig;
import de.slag.central.data.config.DawnFileConfig;
import de.slag.central.data.database.DatabaseConnectionProperties;
import de.slag.central.data.database.DawnHibernateSupport;
import de.slag.central.model.ApplicationBean;

public abstract class AbstractDao<PB extends PersistBean, AB extends ApplicationBean> {

	protected SessionFactory getSessionFactory() {
		final DawnConfig config = DawnFileConfig.instance();
		final DatabaseConnectionProperties creating = DatabaseConnectionProperties.creating(config);
		final DawnHibernateSupport instance = DawnHibernateSupport.getInstance();
		final SessionFactory sessionFactory = instance.getSessionFactory(creating);
		return sessionFactory;
	}

	protected void consumer(Consumer<Session> consumer) {
		function(s -> {
			consumer.accept(s);
			return null;
		});
	}

	protected PB function(Function<Session, PB> function) {
		final Session session = getSessionFactory().openSession();
		final Transaction beginTransaction = session.beginTransaction();
		final PB apply = function.apply(session);
		final PB unproxy = (PB) Hibernate.unproxy(apply);
		beginTransaction.commit();
		session.close();
		return unproxy;
	}

	protected abstract Class<PB> getBeanClass();

	public PB loadById(long id) {
		return function(s -> s.load(getBeanClass(), id));
	}

	public void save(PB bean) {
		FieldMappingUtils.setField(bean, "lastUpdate", new Date());

		if (bean.getId() != null) {
			consumer(s -> s.update(bean));
		} else {
			consumer(s -> s.save(bean));
		}
	}

	public PB create() {
		try {
			return getBeanClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new DataException(e);
		}
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

	public void delete(PB persistBean) {
		final Field validUntil;
		try {
			validUntil = PersistBean.class.getDeclaredField("validUntil");
		} catch (NoSuchFieldException | SecurityException e) {
			throw new DataException(e);
		}
		validUntil.setAccessible(true);

		try {
			validUntil.set(persistBean, new Date());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new DataException(e);
		}
		validUntil.setAccessible(false);
		save(persistBean);
	}

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

	// TEST!
	protected PB loadOneBy(String attribute, String value) {
		String hql = "FROM " + getBeanClass().getName() + " PB WHERE PB." + attribute + " = '" + value + "'";
		final Session session = getSessionFactory().openSession();
		final Query<PB> createQuery = session.createQuery(hql, getBeanClass());
		return createQuery.uniqueResult();
	}
}
