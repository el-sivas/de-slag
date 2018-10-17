package de.slag.finance.data.impl.dev;

import de.slag.central.data.impl.AbstractApplicationBeanDao;
import de.slag.finance.data.SeValuePricePointDao;
import de.slag.finance.model.SeValuePricePoint;

//@Repository
public class SeValuePricePointDaoImpl extends AbstractApplicationBeanDao<PersistSeValuePoint, SeValuePricePoint>
		implements SeValuePricePointDao {

	@Override
	protected Class<PersistSeValuePoint> getBeanClass() {
		return PersistSeValuePoint.class;
	}

}
