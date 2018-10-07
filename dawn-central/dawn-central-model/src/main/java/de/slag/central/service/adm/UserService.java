package de.slag.central.service.adm;

import de.slag.central.model.adm.User;
import de.slag.central.service.ApplicationBeanService;

public interface UserService extends ApplicationBeanService<User> {
	
	User loadByUsername(String username);

}
