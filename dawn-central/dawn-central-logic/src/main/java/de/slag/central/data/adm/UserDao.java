package de.slag.central.data.adm;

import java.util.function.Supplier;

import de.slag.central.data.ApplicationBeanDao;
import de.slag.central.model.adm.User;

public interface UserDao extends ApplicationBeanDao<User> {

	User loadByUsername(String username, Supplier<User> creator);

}
