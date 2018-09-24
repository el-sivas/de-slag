package de.slag.base.app;

import java.util.Collection;
import java.util.Collections;

/**
 * A {@link PersistUtil} is a simple .csv persister for a simple dataset.
 *
 * @param <E>
 */
public interface PersistUtil<E> {

	void save(Collection<E> e, String file);

	default void save(E e, String file) {
		save(Collections.singleton(e), file);
	}

}
