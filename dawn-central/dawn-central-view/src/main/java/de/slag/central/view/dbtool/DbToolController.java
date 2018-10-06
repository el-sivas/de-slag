package de.slag.central.view.dbtool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.BeanFactory;

import de.slag.base.tools.LoggingUtils;
import de.slag.central.DawnApplicationContext;
import de.slag.central.DawnHibernateService;
import de.slag.central.model.adm.User;
import de.slag.central.service.adm.UserService;

@SessionScoped
@ManagedBean(name = "dbToolController")
public class DbToolController {

	private String state;

	private List<String> info = new ArrayList<>();

	@PostConstruct
	public void init() {
		LoggingUtils.activateLogging();
		state = "successfully started";
	}

	public String getState() {
		final String state = this.state;
		this.state = null;
		return state;
	}

	public void addInfo(String s) {
		info.add(0, s);
	}

	public String getInfo() {
		final StringBuilder sb = new StringBuilder();
		info.forEach(e -> sb.append(e + "\n"));
		return sb.toString();
	}

	public Date getTime() {
		return new Date();
	}

	public void initContext() {
		DawnApplicationContext.getContext();

		final Collection<Class<?>> registeredClasses = DawnApplicationContext.getRegisteredClasses();
		final StringBuilder sb = new StringBuilder();
		registeredClasses.forEach(r -> sb.append(r.toGenericString() + "\n"));

		info.add("registered classes:\n" + sb.toString());
		state = "context initialized";
	}

	public void assertDatabaseUpdated() {
		final BeanFactory beanFactory = getBeanFactory();
		final DawnHibernateService dawnHibernateService = beanFactory.getBean(DawnHibernateService.class);
		dawnHibernateService.updateDatabase();
		state = "db update succesful";
		info.add("db update done.");
	}

	public void asssertAdminUser() {
		final UserService userService = getBeanFactory().getBean(UserService.class);

		final String username = "sysadm";
		
		User user = userService.loadByUsername(username);
		if(user == null) {
			user = userService.create();
			user.setUsername("sysadm");
		}		
		
		user.setActive(true);
		user.setPassword("slag");
		userService.save(user);
		info.add("Adm User asserted");

	}

	public BeanFactory getBeanFactory() {
		return DawnApplicationContext.getContext().getAutowireCapableBeanFactory();
	}
}
