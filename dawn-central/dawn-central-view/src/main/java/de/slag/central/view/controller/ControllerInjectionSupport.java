package de.slag.central.view.controller;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import de.slag.central.ApplicationException;
import de.slag.central.DawnApplicationContext;

@ApplicationScoped
@ManagedBean(name = "cis")
public class ControllerInjectionSupport {

	public DawnController get(String controller) throws ClassNotFoundException {
		final Object bean = DawnApplicationContext.getBean(controller);
		if (!(bean instanceof DawnController)) {
			throw new ApplicationException("bean: " + bean + " is not instance of DawnController");
		}
		final DawnController dawnController = (DawnController) bean;
		if (!(dawnController instanceof ApplicationController)) {
			return dawnController;
		}
		final ApplicationController applicationController = (ApplicationController) dawnController;
		asserUserIsLoggedIn(applicationController);
		return applicationController;

	}

	private void asserUserIsLoggedIn(ApplicationController applicationController) {
		if (applicationController.isUserLoggedIn()) {
			return;
		}
		throw new ApplicationException("no user is logged in");
	}
}
