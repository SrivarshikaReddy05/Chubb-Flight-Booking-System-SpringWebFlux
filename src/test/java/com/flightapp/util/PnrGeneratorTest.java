package com.flightapp.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PnrGeneratorTest {

	@Test
	void testGeneratePNR() {
		String pnr1 = PnrGenerator.generatePnr();
		String pnr2 = PnrGenerator.generatePnr();

		assertNotNull(pnr1);
		assertNotNull(pnr2);
		assertNotEquals(pnr1, pnr2);
		assertEquals(6, pnr1.length());
	}
}
