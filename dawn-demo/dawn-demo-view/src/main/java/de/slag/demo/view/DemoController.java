package de.slag.demo.view;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class DemoController {

	public String getDemoInfo() {
		return "arch: view ->  model <- logic -> data\n" + System.currentTimeMillis();
	}

}
