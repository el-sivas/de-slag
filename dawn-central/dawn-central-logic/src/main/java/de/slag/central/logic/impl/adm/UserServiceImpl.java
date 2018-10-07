package de.slag.central.logic.impl.adm;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import de.slag.central.data.ApplicationBeanDao;
import de.slag.central.data.adm.UserDao;
import de.slag.central.logic.impl.AbstractApplicationBeanService;
import de.slag.central.model.adm.User;
import de.slag.central.service.ApplicationBeanCredentials;
import de.slag.central.service.adm.UserService;

@Service
public class UserServiceImpl extends AbstractApplicationBeanService<User> implements UserService {

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
		return ((UserDao)getDao()).loadByUsername(username, getSupplier());
	}
}
