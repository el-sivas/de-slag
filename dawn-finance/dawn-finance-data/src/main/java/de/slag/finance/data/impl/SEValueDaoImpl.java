package de.slag.finance.data.impl;

import org.springframework.stereotype.Repository;

import de.slag.central.data.impl.AbstractApplicationBeanDao;
import de.slag.finance.data.SEValueDao;
import de.slag.finance.model.SEValue;

@Repository
public class SEValueDaoImpl extends AbstractApplicationBeanDao<PersistSEValue, SEValue> implements SEValueDao {

	@Override
	protected Class<PersistSEValue> getBeanClass() {
		return PersistSEValue.class;
	}

}
