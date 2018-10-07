package de.slag.central.view.dbtool.content;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import de.slag.central.model.adm.User;
import de.slag.central.service.adm.UserService;

@Service
public class DbUserServiceImpl implements DbUserService {

	@Resource
	private UserService userService;

	@Override
	public void assertAdminUsers() {
		final String username = "sysadm";

		User user = userService.loadByUsername(username);
		if (user == null) {
			user = userService.create();
			user.setUsername("sysadm");
		}

		user.setActive(true);
		user.setPassword("slag");
		userService.save(user);
	}

}
