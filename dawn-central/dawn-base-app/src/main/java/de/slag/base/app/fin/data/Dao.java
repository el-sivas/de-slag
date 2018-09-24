package de.slag.base.app.fin.data;

import java.util.Collection;
import java.util.Collections;

import de.slag.base.app.fin.domain.DomainEntity;

public interface Dao<D extends DomainEntity> {
	
	default void save(D d) {
		saveAll(Collections.singleton(d));
	}
	
	void saveAll(Collection<D> ds);

}
