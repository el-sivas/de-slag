package de.slag.finance.service.impl.example;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import de.slag.central.data.ApplicationBeanDao;
import de.slag.central.logic.impl.AbstractApplicationBeanService;
import de.slag.central.service.ApplicationBeanCredentials;
import de.slag.finance.data.SEValueDao;
import de.slag.finance.model.SEValue;
import de.slag.finance.service.SEValueService;

@Service
public class SEValueServiceImpl extends AbstractApplicationBeanService<SEValue>
		implements SEValueService {
	
	@Resource
	private SEValueDao sEValueDao;

	@Override
	public SEValue create(Optional<ApplicationBeanCredentials<SEValue>> credentials) {
		return createInternal();
	}

	@Override
	protected Class<SEValue> getBeanClass() {
		return SEValue.class;
	}

	@Override
	protected ApplicationBeanDao<SEValue> getDao() {
		return sEValueDao;
	}
}
