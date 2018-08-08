package de.slag.central.data.dbinfo;

import de.slag.central.data.AbstractDao;

public class DbInfoDaoImpl extends AbstractDao<DbInfo> implements DbInfoDao {

	@Override
	protected Class<DbInfo> getBeanClass() {
		return DbInfo.class;
	}

}
