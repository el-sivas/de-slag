package de.slag.central.view.adm;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import de.slag.central.model.adm.User;
import de.slag.central.service.adm.UserService;
import de.slag.central.view.controller.ApplicationController;

@Controller
public class AdmController extends ApplicationController {

	private static final long serialVersionUID = 1L;
	
	@Resource
	private UserService userService;
	
	public Collection<User> getAllUsers() {
		return userService.findAll();
	}

}
