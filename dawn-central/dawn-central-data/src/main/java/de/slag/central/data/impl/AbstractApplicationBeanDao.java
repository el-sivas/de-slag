package de.slag.central.data.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.query.Query;

import de.slag.base.tools.mapping.FieldMapper;
import de.slag.central.data.FieldMapperSupport;
import de.slag.central.data.transfer.PersistDataTransferService;
import de.slag.central.model.ApplicationBean;

public abstract class AbstractApplicationBeanDao<PB extends PersistBean, AB extends ApplicationBean>
		extends AbstractPersistBeanDao<PB> {

	@Resource
	private PersistDataTransferService persistDataTransferService;

	private final BiConsumer<AB, PB> AP_NO_SPECIAL_TRANSFER = (a, p) -> {
	};
	private final BiConsumer<PB, AB> PA_NO_SPECIAL_TRANSFER = (p, a) -> {
	};

	private final BiConsumer<PB, AB> MAP_PERSIST_TO_APPLICATION = (p, a) -> {
		persistDataTransferService.transferData(p, a);
		getSpecialTransferPA().accept(p, a);
	};

	private final BiConsumer<AB, PB> MAP_APPLICATION_TO_PERSIST = (a, p) -> {
		persistDataTransferService.transferData(a, p);
		getSpecialTransferAP().accept(a, p);
	};

	protected BiConsumer<PB, AB> getSpecialTransferPA() {
		return PA_NO_SPECIAL_TRANSFER;
	}

	protected BiConsumer<AB, PB> getSpecialTransferAP() {
		return AP_NO_SPECIAL_TRANSFER;
	}

	public Optional<AB> loadBy(long id, Supplier<AB> beanSupplier) {
		final PB persistBean = loadById(id);
		if (persistBean == null) {
			return Optional.empty();
		}
		return Optional.of(one(persistBean, beanSupplier));
	}

	public void save(AB bean) {
		final Long id = bean.getId();

		final PB persistBean;
		if (id != null) {
			persistBean = loadById(id);
		} else {
			persistBean = create();
		}
		MAP_APPLICATION_TO_PERSIST.accept(bean, persistBean);
		save(persistBean);
	}

	public FieldMapper<?> getFieldMapper(Class<?> from, Class<?> to) {
		return FieldMapperSupport.instance().get(from, to);
	}

	public void delete(AB bean) {
		final PB persistBean = loadById(bean.getId());
		delete(persistBean);
		MAP_PERSIST_TO_APPLICATION.accept(persistBean, bean);
	}

	public void deleteBy(Long id) {
		final PB loadById = loadById(id);
		if (loadById == null) {
			return;
		}
		delete(loadById);
	}

	public Collection<AB> findAll(Supplier<AB> beanSupplier) {
		return all(findAll(), beanSupplier);
	}

	protected <T> AB one(PB pb, Supplier<AB> creator) {
		final AB ab = creator.get();
		MAP_PERSIST_TO_APPLICATION.accept(pb, ab);
		return ab;
	}

	protected Collection<AB> all(Collection<PB> all, Supplier<AB> creator) {
		return all.stream().map(p -> one(p, creator)).collect(Collectors.toList());
	}

	protected <T> AB loadBy(String hql, Function<Query<PB>, PB> function, Supplier<AB> creator) {
		try (Session session = openSession()) {
			final PB persistBean = function.apply(session.createQuery(hql, getBeanClass()));
			if (persistBean == null) {
				return null;
			}

			return one(persistBean, creator);
		}
	}
}
