package de.slag.finance.service.impl;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import de.slag.central.data.ApplicationBeanDao;
import de.slag.central.logic.impl.AbstractApplicationBeanService;
import de.slag.central.service.ApplicationBeanCredentials;
import de.slag.finance.data.SeValuePricePointDao;
import de.slag.finance.model.SeValuePricePoint;
import de.slag.finance.service.SeValuePricePointService;

@Service
public class SeValuePricePointServiceImpl extends AbstractApplicationBeanService<SeValuePricePoint>
		implements SeValuePricePointService {
	
	@Resource
	private SeValuePricePointDao seValuePricePointDao;

	@Override
	public SeValuePricePoint create(Optional<ApplicationBeanCredentials<SeValuePricePoint>> credentials) {
		return new SeValuePricePoint();
	}

	@Override
	protected Class<SeValuePricePoint> getBeanClass() {
		return SeValuePricePoint.class;
	}

	@Override
	protected ApplicationBeanDao<SeValuePricePoint> getDao() {
		return seValuePricePointDao;
	}

}
