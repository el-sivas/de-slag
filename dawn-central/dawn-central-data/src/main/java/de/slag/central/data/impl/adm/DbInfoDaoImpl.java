package de.slag.central.data.impl.adm;

import org.springframework.stereotype.Repository;

import de.slag.central.data.adm.DbInfoDao;
import de.slag.central.data.impl.AbstractPersistBeanDao;
import de.slag.central.data.model.DbInfo;

@Repository
public class DbInfoDaoImpl extends AbstractPersistBeanDao<DbInfo> implements DbInfoDao {

	@Override
	protected Class<DbInfo> getBeanClass() {
		return DbInfo.class;
	}

	@Override
	public void saveInfo(String info) {
		final DbInfo dbInfo = create();
		dbInfo.setInfo(info);
		save(dbInfo);
	}

}
