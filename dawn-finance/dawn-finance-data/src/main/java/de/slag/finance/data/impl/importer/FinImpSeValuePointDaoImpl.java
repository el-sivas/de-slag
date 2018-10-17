package de.slag.finance.data.impl.importer;

import org.springframework.stereotype.Repository;

import de.slag.central.data.impl.AbstractPersistBeanDao;

@Repository
public class FinImpSeValuePointDaoImpl extends AbstractPersistBeanDao<FinImpSeValuePoint>
		implements FinImpSeValuePointDao {

	@Override
	protected Class<FinImpSeValuePoint> getBeanClass() {
		return FinImpSeValuePoint.class;
	}
}
