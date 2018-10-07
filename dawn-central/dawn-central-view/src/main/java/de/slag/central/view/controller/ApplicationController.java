package de.slag.central.view.controller;

import javax.annotation.Resource;

import de.slag.central.model.adm.User;
import de.slag.central.view.SessionContext;

public class ApplicationController implements DawnController {

	@Resource
	private SessionContext sessionContext;

	protected User getCurrentUser() {
		return sessionContext.getCurrentUser();
	}

	public boolean isUserLoggedIn() {
		return getCurrentUser() != null;
	}

}
