package de.slag.central.view.controller.dev;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.slag.central.DawnApplicationContext;

@SessionScoped
@ManagedBean
public class DevController {

	private static final Log LOG = LogFactory.getLog(DevController.class);

	private String info = "info:";

	public void test() {
		
	}

	private void add(String s) {
		info = info == null ? "" : info + s + "\n";
	}

	public String getInfo() {
		return info;
	}
}
