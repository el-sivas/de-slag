package de.slag.central.view.controller;

import de.slag.central.model.ApplicationBean;
import de.slag.central.service.ApplicationBeanService;

public abstract class ApplicationBeanController<AB extends ApplicationBean> extends ApplicationController {

	protected abstract ApplicationBeanService<AB> getApplicationBeanService();
}
