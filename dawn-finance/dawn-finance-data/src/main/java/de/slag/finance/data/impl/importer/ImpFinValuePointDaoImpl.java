package de.slag.finance.data.impl.importer;

import org.springframework.stereotype.Repository;

import de.slag.central.data.impl.AbstractPersistBeanDao;

@Repository
public class ImpFinValuePointDaoImpl extends AbstractPersistBeanDao<ImpFinValuePoint>
		implements ImpFinValuePointDao {

	@Override
	protected Class<ImpFinValuePoint> getBeanClass() {
		return ImpFinValuePoint.class;
	}
}
