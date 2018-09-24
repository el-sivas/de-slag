package de.slag.base.context.test;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("contextAdvancedTestService")
public class ContextAdvancedTestServiceImpl implements ContextAdvancedTestService {

	@Override
	public String advancedTest() {
		return "advanced test this";
	}

}
