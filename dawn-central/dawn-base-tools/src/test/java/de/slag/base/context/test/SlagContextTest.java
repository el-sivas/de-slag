package de.slag.base.context.test;

import org.junit.Test;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import de.slag.base.context.SlagContext;

public class SlagContextTest {

	@Test
	public void test() {
		SlagContext.init();
		final ConfigurableListableBeanFactory beanFactory = SlagContext.getContext().getBeanFactory();
		final ContextTestService bean = beanFactory.getBean(ContextTestService.class);
		System.out.println(bean.testThis());
		System.out.println(bean.advanced());
		
	}

}
