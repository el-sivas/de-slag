package de.slag.central.data.impl;

import java.util.Collection;
import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Function;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import de.slag.base.tools.ReflectionUtils;

public abstract class AbstractPersistBeanDao<PB extends PersistBean> extends AbstractDao {

	protected static final String COL_VALID_UNTIL = "validUntil";

	protected final Function<Query<PB>, PB> RESULT_UNIQUE = q -> q.uniqueResult();

	protected final Function<Query<PB>, Collection<PB>> RESULT_COLLECTION = q -> q.list();

	protected PB function(Function<Session, PB> function) {
		try (final Session session = openSession()) {
			final Transaction beginTransaction = session.beginTransaction();
			final PB apply = function.apply(session);
			final PB unproxy = (PB) Hibernate.unproxy(apply);
			beginTransaction.commit();
			return unproxy;
		}
	}

	protected abstract Class<PB> getBeanClass();

	public PB loadById(long id) {
		return function(s -> s.load(getBeanClass(), id));
	}

	protected void consumer(Consumer<Session> consumer) {
		function(s -> {
			consumer.accept(s);
			return null;
		});
	}

	public void save(PB bean) {
		ReflectionUtils.setField(bean, "lastUpdate", new Date());

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

	public void delete(PB persistBean) {
		ReflectionUtils.setField(persistBean, COL_VALID_UNTIL, new Date());
		save(persistBean);
	}

	public Collection<PB> findAll() {
		return execute(HqlSelect.getHql(getBeanClass()), RESULT_COLLECTION);
	}

	protected <T> T execute(String hql, Function<Query<PB>, T> function) {
		try (Session session = openSession()) {
			return function.apply(session.createQuery(hql, getBeanClass()));
		}
	}

	@Deprecated // nur als Test
	protected PB loadOneBy(String attribute, String value) {
		String hql = "FROM " + getBeanClass().getName() + " PB WHERE PB." + attribute + " = '" + value + "'";
		final Session session = getSessionFactory().openSession();
		final Query<PB> createQuery = session.createQuery(hql, getBeanClass());
		return createQuery.uniqueResult();
	}
}
