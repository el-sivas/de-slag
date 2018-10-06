package de.slag.central;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

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
		if (applicationContext != null) {
			throw new ApplicationException("context already initialized");
		}
		LOG.info("init context...");
		applicationContext = new GenericApplicationContext();
		final ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();

		final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
				false);

		provider.addIncludeFilter(new AnnotationTypeFilter(Service.class));
		provider.addIncludeFilter(new AnnotationTypeFilter(Component.class));
		provider.addIncludeFilter(new AnnotationTypeFilter(Repository.class));

		final Set<BeanDefinition> components = provider.findCandidateComponents("de/slag");
		if (components.isEmpty()) {
			LOG.warn("no components for registration found.");
			return;
		}
		
		for (BeanDefinition component : components) {
			final String beanClassName = component.getBeanClassName();
			final Class<?> cls;
			try {
				cls = Class.forName(beanClassName);
			} catch (ClassNotFoundException e) {
				throw new ApplicationException(e);
			}
			String name;
			if (cls.isAnnotationPresent(Service.class) || cls.isAnnotationPresent(Repository.class)) {
				name = beanClassName.substring(0, beanClassName.length() - "Impl".length());
			} else {
				name = beanClassName;
			}
			final Object newInstance;
			try {
				newInstance = cls.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new ApplicationException(e);
			}
			LOG.info("register: " + newInstance + " with name: " + name);
			beanFactory.registerSingleton(name, newInstance);
			registeredClasses.add(cls);
		}
		applicationContext.refresh();
	}

	public static Collection<Class<?>> getRegisteredClasses() {
		return new ArrayList<>(registeredClasses);
	}
}
