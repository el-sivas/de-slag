package de.slag.central.view.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import de.slag.central.model.adm.User;
import de.slag.central.service.adm.UserService;
import de.slag.central.view.SessionContext;

@Controller
public class LoginController implements DawnController {

	private static final long serialVersionUID = 1L;

	private static final String START_PAGE = "start.xhtml";

	@Resource
	private SessionContext sessionContext;

	@Resource
	private UserService userService;

	private String msg;

	private String username;

	private String password;

	public boolean isContextAvailable() {
		return sessionContext.isContextAvaliable();
	}

	public String login() {
		final User user = userService.loadByUsername(username);
		if (user == null) {
			msg = "user not found: " + username;
			return null;
		}
		if (!user.isActive()) {
			userService.incrementFailedLogins(user);
			msg = "user not active: " + username;
			return null;
		}
		if (!user.isPasswordCorrect(password)) {
			userService.incrementFailedLogins(user);
			msg = "password incorrect, user: " + username;
			return null;
		}
		userService.setLastLogin(user);
		sessionContext.setCurrentUser(user);

		return START_PAGE;
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

	public String getMsg() {
		final String msg = this.msg;
		this.msg = null;
		return msg;
	}
}
