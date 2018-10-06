package de.slag.central.view.adm;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.slag.central.model.adm.User;
import de.slag.central.service.ApplicationBeanService;
import de.slag.central.service.adm.UserService;
import de.slag.central.view.controller.ApplicationBeanController;

@SessionScoped
@ManagedBean
public class UserController extends ApplicationBeanController<User> {
	
	private UserService userService;

	@Override
	protected ApplicationBeanService<User> getApplicationBeanService() {
		return userService;
	}

}
