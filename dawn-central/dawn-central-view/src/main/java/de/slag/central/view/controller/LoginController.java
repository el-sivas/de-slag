package de.slag.central.view.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import de.slag.base.tools.SleepUtils;
import de.slag.central.model.adm.User;
import de.slag.central.service.adm.UserService;
import de.slag.central.view.SessionContext;
import de.slag.central.view.ViewException;

@Controller
public class LoginController implements DawnController {

	private static final String START_PAGE = "start.xhtml";

	@Resource
	private SessionContext sessionContext;

	@Resource
	private UserService userService;

	private String username;

	private String password;

	public String login() {
		final User user = userService.loadByUsername(username);
		if (user == null) {
			throw new ViewException("user not found", username);
		}
		if (!user.isPasswordCorrect(password)) {
			throw new ViewException("password incorrect", user);
		}
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
}
