package de.slag.finance.service.impl;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import de.slag.central.data.ApplicationBeanDao;
import de.slag.central.logic.impl.AbstractApplicationBeanService;
import de.slag.central.service.ApplicationBeanCredentials;
import de.slag.finance.data.SeValueDao;
import de.slag.finance.data.SeValuePricePointDao;
import de.slag.finance.model.SeValue;
import de.slag.finance.service.SeValueService;

@Service
public class SeValueServiceImpl extends AbstractApplicationBeanService<SeValue>
		implements SeValueService {
	
	@Resource
	private SeValueDao seValueDao;

	@Override
	public SeValue create(Optional<ApplicationBeanCredentials<SeValue>> credentials) {
		return createInternal();
	}

	@Override
	protected Class<SeValue> getBeanClass() {
		return SeValue.class;
	}

	@Override
	protected ApplicationBeanDao<SeValue> getDao() {
		return seValueDao;
	}

	@Override
	public Optional<SeValue> loadByIsin(String isin) {
		return findAll().stream().filter(e -> isin.equals(e.getIsin())).findFirst();
	}
}
