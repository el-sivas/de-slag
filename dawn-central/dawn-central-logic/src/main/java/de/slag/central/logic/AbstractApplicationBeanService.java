package de.slag.central.logic;

import de.slag.central.data.PersistBean;
import de.slag.central.data.PersistBeanDao;
import de.slag.central.model.ApplicationBean;

public abstract class AbstractApplicationBeanService<AB extends ApplicationBean, PB extends PersistBean> {

	protected abstract PersistBeanDao<PB> getDao();

	public AB loadBy(long id) {
		return to(getDao().loadBy(id));
	}

	public void save(AB bean) {
		getDao().save(to(bean));
	}
	
	public void delete(AB bean) {
		getDao().delete(to(bean));
	}

	protected abstract PB to(AB bean);

	protected abstract AB to(PB bean);
}
