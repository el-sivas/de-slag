package de.slag.central.view.adm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;

import de.slag.central.model.adm.User;
import de.slag.central.service.adm.UserService;
import de.slag.central.view.controller.ApplicationController;

@Controller
public class AdmController extends ApplicationController {

	private static final long serialVersionUID = 1L;

	@Resource
	private UserService userService;

	private Collection<User> users = new ArrayList<>();

	private String username;

	private String password;

	@Override
	public void reset() {
		users.clear();
		users.addAll(userService.findAll());
		addInfo(users.size() + " users found");
	}

	public Collection<User> getAllUsers() {
		return users;
	}

	public void createUser() {
		if (StringUtils.isBlank(username)) {
			addError("username must not be empty");
			return;
		}
		if (StringUtils.isBlank(password)) {
			addError("passwort must not be empty");
			return;
		}
		final User user = userService.create();
		user.setUsername(username);
		user.setPassword(password);
		user.setActive(false);
		userService.save(user);
		username = null;
		password = null;
		addInfo("user created: " + user);
		reset();
	}

	public void userActivateDeactivate(Long id) {
		final User user = userService.loadBy(id);
		final StringBuilder sb = new StringBuilder();
		sb.append("user ");
		if (user.isActive()) {
			userService.deactivate(user);
			sb.append("deactivated: ");
		} else {
			userService.activate(user);
			userService.resetFailedLogins(user);
		}
		sb.append(user.getUsername());
		addInfo(sb.toString());
		reset();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Object[]> getSystemInfo() {

		final Collection<Object[]> info = new ArrayList<>();
		final Properties properties = System.getProperties();
		final Set<Object> keySet = properties.keySet();
		final List o = new ArrayList<>();
		o.addAll(keySet);
		Collections.sort(o);
		
		o.forEach(k -> info.add(new Object[] { k, properties.get(k) }));

		return info;

	}
}
