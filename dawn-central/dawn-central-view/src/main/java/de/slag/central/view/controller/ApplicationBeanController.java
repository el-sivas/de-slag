package de.slag.central.view.controller;

import de.slag.central.model.ApplicationBean;
import de.slag.central.service.ApplicationBeanService;

public abstract class ApplicationBeanController<AB extends ApplicationBean> extends ApplicationController {

	private static final long serialVersionUID = 1L;

	protected abstract ApplicationBeanService<AB> getApplicationBeanService();
	
	private AB value;

	public AB create() {
		return getApplicationBeanService().create();
	}

	public void save(AB bean) {
		getApplicationBeanService().save(bean);
		reset();
	}

	public void delete(Long id) {
		getApplicationBeanService().delete(id);
		addInfo("deleted id: " + id);
		reset();
	}

	public void reset() {
		// implement if needet
	}

	public AB getValue() {
		return value;
	}

	public void setValue(AB value) {
		this.value = value;
	}
	
	public void open(Long id) {
		setValue(getApplicationBeanService().loadBy(id));
	}
	
	public void close() {
		setValue(null);
		reset();
	}
}
