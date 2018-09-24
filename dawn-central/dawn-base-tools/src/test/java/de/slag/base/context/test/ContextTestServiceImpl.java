package de.slag.base.context.test;

import org.springframework.stereotype.Service;

@Service
public class ContextTestServiceImpl implements ContextTestService {

	private ContextAdvancedTestService contextAdvancedTestService = getBean(ContextAdvancedTestService.class);

	@Override
	public String testThis() {
		return "this is a test";
	}

	@Override
	public String advanced() {
		return contextAdvancedTestService.advancedTest();
	}

}
