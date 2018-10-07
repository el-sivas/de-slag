package de.slag.central;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DawnApplicationContext {

	private static final Log LOG = LogFactory.getLog(DawnApplicationContext.class);

	private static ConfigurableApplicationContext applicationContext;

	private static Collection<Class<?>> registeredClasses = new ArrayList<>();

	private DawnApplicationContext() {

	}

	public static ApplicationContext getContext() {
		if (applicationContext == null) {
			init();
		}
		return applicationContext;
	}

	private static void init() {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(DawnConfig.class);
		ctx.refresh();
		applicationContext = ctx;
	}

	public static <T> T getBean(Class<T> requiredType) {
		return getContext().getBean(requiredType);
	}
	
	public static Object getBean(String name) {
		return getContext().getBean(name);
	}

	public static Collection<Class<?>> getRegisteredClasses() {
		return new ArrayList<>(registeredClasses);
	}
}
