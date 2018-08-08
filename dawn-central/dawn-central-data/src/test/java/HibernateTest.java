import org.junit.Before;
import org.junit.Test;

import de.slag.central.data.config.DawnFileConfig;
import de.slag.central.data.database.DatabaseConnectionProperties;
import de.slag.central.data.database.DawnHibernateSupport;
import de.slag.central.data.dbinfo.DbInfo;
import de.slag.central.data.dbinfo.DbInfoDao;
import de.slag.central.data.dbinfo.DbInfoDaoImpl;

public class HibernateTest {

	DbInfoDao dbInfoDao = new DbInfoDaoImpl();

	@Before
	public void init() {
		DawnHibernateSupport.getInstance()
				.updateDatabase(DatabaseConnectionProperties.creating(DawnFileConfig.instance()));
	}

	@Test
	public void test() {
		dbInfoDao.save(new DbInfo("info", "text: " + System.currentTimeMillis()));
		dbInfoDao.delete(dbInfoDao.loadBy(1));
	}

}
