package de.slag.central.data;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Function;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.slag.central.data.config.DawnConfig;
import de.slag.central.data.config.DawnFileConfig;
import de.slag.central.data.database.DatabaseConnectionProperties;
import de.slag.central.data.database.DawnHibernateSupport;

public abstract class AbstractDao<PB extends PersistBean> {

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
		final PB apply = function.apply(session);
		session.close();
		return apply;
	}

	protected abstract Class<PB> getBeanClass();

	public PB loadBy(long id) {
		return function(s -> s.load(getBeanClass(), id));
	}

	public void save(PB bean) {
		if (bean.getId() != null) {
			consumer(s -> s.update(bean));
		} else {
			consumer(s -> s.save(bean));
		}
	}

	public void delete(PB bean) {
		final Field validUntil;
		try {
			validUntil = PersistBean.class.getDeclaredField("validUntil");
		} catch (NoSuchFieldException | SecurityException e) {
			throw new DataException(e);
		}
		validUntil.setAccessible(true);
		
		try {
			validUntil.set(bean, new Date());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new DataException(e);
		}
		validUntil.setAccessible(false);
		save(bean);
	}
}
