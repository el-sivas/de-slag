package de.slag.central.view.adm;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import de.slag.central.model.adm.User;
import de.slag.central.service.ApplicationBeanService;
import de.slag.central.service.adm.UserService;
import de.slag.central.view.controller.ApplicationBeanController;

@Controller
public class UserController extends ApplicationBeanController<User> {

	@Resource
	private UserService userService;

	@Override
	protected ApplicationBeanService<User> getApplicationBeanService() {
		return userService;
	}

}
