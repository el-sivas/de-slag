package de.slag.base.app.test;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestApp {
	
	public static void main(String[] args) {
		final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(TestServiceImpl.class);
		final ConfigurableListableBeanFactory beanFactory = ctx.getBeanFactory();
		final TestService bean = beanFactory.getBean(TestService.class);
		System.out.println(bean.testThis());
	}
}
