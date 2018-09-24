package de.slag.base.app.test;

import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

	@Override
	public String testThis() {
		return "this test";
	}

}
