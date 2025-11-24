package com.flightapp.util;

import java.security.SecureRandom;

public class PnrGenerator {
	private static final String ALPHANUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final SecureRandom RANDOM = new SecureRandom();

	public static String generatePnr() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			sb.append(ALPHANUM.charAt(RANDOM.nextInt(ALPHANUM.length())));
		}
		return sb.toString();
	}
}