package de.slag.finance.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;

import de.slag.central.data.ApplicationBeanDao;
import de.slag.finance.model.SeValuePricePoint;

public interface SeValuePricePointDao extends ApplicationBeanDao<SeValuePricePoint> {

	static SeValuePricePointDao fake() {
		return new SeValuePricePointDao() {

			private Set<SeValuePricePoint> all = new TreeSet<>();

			@Override
			public void save(SeValuePricePoint bean) {
				all.add(bean);

			}

			@Override
			public Optional<SeValuePricePoint> loadBy(long id, Supplier<SeValuePricePoint> beanSupplier) {
				return all.stream().filter(e -> e.getId() == id).findFirst();
			}

			@Override
			public Collection<SeValuePricePoint> findAll(Supplier<SeValuePricePoint> beanSupplier) {
				return new ArrayList<>(all);
			}

			@Override
			public void deleteBy(Long id) {
				Optional<SeValuePricePoint> loadBy = loadBy(id, null);
				if (!loadBy.isPresent()) {
					return;
				}
				delete(loadBy.get());

			}

			@Override
			public void delete(SeValuePricePoint bean) {
				all.remove(bean);
			}
		};
	}
}
