package de.slag.central.view.dbtool.structure;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import de.slag.central.service.adm.DawnHibernateService;

@Service
public class DbUpdateServiceImpl implements DbUpdateService {
	
	@Resource
	private DawnHibernateService dawnHibernateService;

	@Override
	public void updateDatabase() {
		dawnHibernateService.updateDatabase();
	}	
}
