package de.slag.central.logic.impl.adm;

import java.util.Date;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import de.slag.central.data.ApplicationBeanDao;
import de.slag.central.data.adm.UserDao;
import de.slag.central.logic.impl.AbstractApplicationBeanService;
import de.slag.central.model.adm.User;
import de.slag.central.service.ApplicationBeanCredentials;
import de.slag.central.service.adm.UserService;

@Service
public class UserServiceImpl extends AbstractApplicationBeanService<User> implements UserService {

	private static final Log LOG = LogFactory.getLog(UserServiceImpl.class);

	private static final int MAX_FAILED_LOGINS = 5;

	@Resource
	private UserDao userDao;

	@Override
	public User create(Optional<ApplicationBeanCredentials<User>> credentials) {
		return getSupplier().get();
	}

	@Override
	protected Class<User> getBeanClass() {
		return User.class;
	}

	@Override
	protected ApplicationBeanDao<User> getDao() {
		return userDao;
	}

	@Override
	public User loadByUsername(String username) {
		return ((UserDao) getDao()).loadByUsername(username, getSupplier());
	}

	@Override
	public void activate(User user) {
		user.setActive(true);
		save(user);
		LOG.info("User activated: " + user.getUsername());

	}

	@Override
	public void deactivate(User user) {
		user.setActive(false);
		save(user);
		LOG.info("User deactivated: " + user.getUsername());

	}

	@Override
	public void resetFailedLogins(User user) {
		user.setFailedLogins(0);
		save(user);
		LOG.info("Reset failed logins, user: " + user.getUsername());
	}

	@Override
	public void incrementFailedLogins(User user) {
		user.setFailedLogins(user.getFailedLogins() + 1);
		if(user.getFailedLogins() >= MAX_FAILED_LOGINS) {
			deactivate(user);
		}
		save(user);
	}
	
	@Override
	public void setLastLogin(User user) {
		user.setLastLogin(new Date());
		save(user);
	}
}
