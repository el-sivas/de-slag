package de.slag.central.view.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;

import de.slag.central.DawnApplicationContext;
import de.slag.central.model.adm.User;
import de.slag.central.view.SessionContext;
import de.slag.central.view.ViewException;

public class ApplicationController {

	@ManagedProperty(value = "#{sessionContext}")
	private SessionContext sessionContext;
	
	private ApplicationContext applicationContext;

	public void setSessionContext(SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}
	
	@PostConstruct
	public void init() {
		assertLoggedInUser();
		applicationContext = DawnApplicationContext.getContext();
	}

	public void assertLoggedInUser() {
		if (getCurrentUser() == null) {
			throw new ViewException("no user logged in");
		}
	}
	
	protected BeanFactory getBeanFactory() {
		return applicationContext.getAutowireCapableBeanFactory();
	}
	protected User getCurrentUser() {
		return sessionContext.getCurrentUser();
	}

}
