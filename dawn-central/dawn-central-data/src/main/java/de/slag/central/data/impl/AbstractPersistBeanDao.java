package de.slag.central.data.impl;

import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Function;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import de.slag.base.tools.ReflectionUtils;

public abstract class AbstractPersistBeanDao<PB extends PersistBean> extends AbstractDao {

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
		ReflectionUtils.setField(persistBean, "validUntil", new Date());
		save(persistBean);
	}
}
