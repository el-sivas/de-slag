package de.slag.finance.service;

import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;

public class IsinValidator implements Predicate<String> {

	private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	@Override
	public boolean test(String isinCandidate) {
		if (StringUtils.isBlank(isinCandidate)) {
			return false;
		}

		if (isinCandidate.length() != 12) {
			return false;
		}

		if (!areLetters(isinCandidate.substring(0, 1), isinCandidate.substring(1, 2))) {
			return false;
		}

		return true;
	}

	private boolean areLetters(String... strings) {
		for (String letter : strings) {
			if (!isLetter(letter)) {
				return false;
			}
		}
		return true;
	}

	private boolean isLetter(String letter) {
		if (!LETTERS.contains(letter)) {
			return false;
		}
		return true;
	}
}
