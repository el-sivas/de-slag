package de.slag.central.logic.impl.adm;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import de.slag.central.data.adm.DbInfoDao;
import de.slag.central.service.adm.DbInfoService;

@Service
public class DbInfoServiceImpl implements DbInfoService {
	
	@Resource
	private DbInfoDao dbInfoDao;
	
	@Override
	public void saveInfo(String info) {
		dbInfoDao.saveInfo(info);
	}

}
