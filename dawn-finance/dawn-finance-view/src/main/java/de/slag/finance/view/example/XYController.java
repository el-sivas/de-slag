package de.slag.finance.view.example;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import de.slag.central.view.controller.ApplicationController;

@SessionScoped
@ManagedBean(name = "xyController")
public class XYController extends ApplicationController
{
	@ManagedProperty(value = "#{testBean}")
	TestBean testBean;
	
	public void setTestBean(TestBean testBean) {
		this.testBean = testBean;
	}
	

	public String getTest() {
		return getCurrentUser().getUsername();
	}




}
