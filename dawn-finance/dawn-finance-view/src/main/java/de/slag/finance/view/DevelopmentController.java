package de.slag.finance.view;

import org.springframework.stereotype.Controller;

import de.slag.base.SlagDevelopment;
import de.slag.central.view.controller.ApplicationController;
import de.slag.central.view.controller.DawnController;

@Controller
public class DevelopmentController extends ApplicationController implements DawnController {

	private static final long serialVersionUID = 1L;

	public boolean isDevelopmentEnabled() {
		return SlagDevelopment.isEnabled();
	}

}
