package com.tus.rates;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class CalculateUtilityBillTest {

	private CalculateUtilityBill cub;

	private static final int[] NEG_ELEC = { -10, 10, 10 };

	@BeforeEach
	public void setUp() throws Exception {
		cub = new CalculateUtilityBill();
	}

	static Stream<Arguments> invalidNumValuesUsages() {
		return Stream.of(
				Arguments.of(0, new int[] {}), 
				Arguments.of(2, new int[] { 150, 40 }),
				Arguments.of(4, new int[] { 100, 200, 300, 400 }));
	}

	@ParameterizedTest(name = "should have invalid number of values: {0}")
	@MethodSource("invalidNumValuesUsages")
	void testInvalidNumValuesUsages(int invalidLength, int[] usages) {
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			cub.calculateBill(usages, false);});
		assertEquals("Invalid number of usage values: expected 3, found " + invalidLength, exception.getMessage());
	}

	@ParameterizedTest(name = "Electricity should have invalid value: {0}")
	@ValueSource(ints = { -1, -2 })
	void testInvalidValueElectricity(int invalidNum) {
		int[] usages = { invalidNum, 5, 5 };
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			cub.calculateBill(usages, false);
		});
		assertEquals("Usage cannot be negative: " + invalidNum + " KWh of electricity", exception.getMessage());
	}

	@ParameterizedTest(name = "Water should have invalid value: {0}")
	@ValueSource(ints = { -1, -2 })
	void testInvalidValueWater(int invalidNum) {
		int[] usages = { 5, invalidNum, 5 };
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			cub.calculateBill(usages, false);
		});
		assertEquals("Usage cannot be negative: " + invalidNum + " cubic meters of water", exception.getMessage());
	}

	@ParameterizedTest(name = "Gas should have invalid value: {0}")
	@ValueSource(ints = { -1, -2 })
	void testInvalidValueGas(int invalidNum) {
		int[] usages = { 5, 5, invalidNum };
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			cub.calculateBill(usages, false);
		});
		assertEquals("Usage cannot be negative: " + invalidNum + " cubic meters of gas", exception.getMessage());
	}

	@ParameterizedTest(name = "final bill for electricity: {0}, water: {1}, gas: {2}, solar panels: {3}, should be: {4}")
	@CsvFileSource(resources = "/test_bills.csv", numLinesToSkip = 1)
	void testBills(int elec, int water, int gas, boolean solarPanels, double expectedResult) {
		int[] usages = { elec, water, gas };
		assertEquals(expectedResult, cub.calculateBill(usages, solarPanels));
	}

}
