package de.slag.central.data;

public interface PersistBeanDao<PB extends PersistBean> {
	
	PB loadBy(long id);
	
	void save(PB bean);
	
	void delete(PB bean);

}
