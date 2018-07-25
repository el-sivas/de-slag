package de.slag.central.view;

import de.slag.central.model.ApplicationBean;
import de.slag.central.service.ApplicationBeanService;

public abstract class ApplicationBeanController<AB extends ApplicationBean> {

	abstract ApplicationBeanService<AB> getApplicationBeanService();
	
}
