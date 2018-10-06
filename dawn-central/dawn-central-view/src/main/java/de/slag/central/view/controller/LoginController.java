package de.slag.central.view.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import de.slag.base.tools.SleepUtils;
import de.slag.central.model.adm.User;
import de.slag.central.service.adm.UserService;
import de.slag.central.view.SessionContext;
import de.slag.central.view.ViewException;

@SessionScoped
@ManagedBean(name = "loginController")
public class LoginController {

	@ManagedProperty(value = "#{sessionContext}")
	private SessionContext sessionContext;

	private UserService userService;

	private String username;

	private String password;

	public String login() {
		if (false) {

			final User user = userService.loadByUsername(username);
			if (user == null) {
				throw new ViewException("user not found");
			}
			if (!user.isPasswordCorrect(password)) {
				SleepUtils.sleepFor(1000);
				throw new ViewException("password incorrect");
			}
			sessionContext.setCurrentUser(user);
		} else {
			final User user = new User();
			user.setUsername("sysadm");
			sessionContext.setCurrentUser(user);
		}
		return "start.xhtml";
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

	public void setSessionContext(SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}

}
