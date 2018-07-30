package de.slag.central.data.user;

import de.slag.central.data.AbstractDao;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {

	@Override
	protected Class<User> getBeanClass() {
		return User.class;
	}

}
