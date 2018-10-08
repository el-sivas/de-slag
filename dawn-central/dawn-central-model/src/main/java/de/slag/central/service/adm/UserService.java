package de.slag.central.service.adm;

import de.slag.central.model.adm.User;
import de.slag.central.service.ApplicationBeanService;

public interface UserService extends ApplicationBeanService<User> {

	User loadByUsername(String username);

	void activate(User user);

	void deactivate(User user);
	
	void resetFailedLogins(User user);

	void incrementFailedLogins(User user);

	void setLastLogin(User user);

}
