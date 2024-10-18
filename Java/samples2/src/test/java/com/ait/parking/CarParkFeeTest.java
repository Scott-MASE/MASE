package com.ait.parking;

import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class CarParkFeeTest {

	private CarParkFee carParkFee;

	@BeforeEach
	public void setUp() {
		carParkFee = new CarParkFee();
	}

	static Stream<Arguments> hoursOutOfRange() {
		return Stream.of(Arguments.of(-2, new int[] { 3, -2, 3, 4, 19 }), // -2
				Arguments.of(-1, new int[] { 3, 3, -1, 3, 3 }), // -1
				Arguments.of(25, new int[] { 3, 3, 25, 3, 3 }), // 25
				Arguments.of(26, new int[] { 3, 3, 26, 3, 3 })// 26
		);
	}

	static Stream<Arguments> invalidNumMarksValues() {
		return Stream.of(Arguments.of(3, new int[] { 3, 4, 19 }), // 3
				Arguments.of(4, new int[] { 3, 3, 3, 3 }), // 4
				Arguments.of(6, new int[] { 3, 3, 20, 3, 3, 21 }), // 6
				Arguments.of(7, new int[] { 3, 3, 22, 3, 3, 19, 18 })// 7
		);
	}

	static Stream<Arguments> hoursInRange() {
		return Stream.of(Arguments.of(10, new int[] { 0, 0, 0, 0, 0 }), // 0
				Arguments.of(10, new int[] { 1, 0, 0, 0, 0 }), // 1
				Arguments.of(10, new int[] { 10, 6, 1, 1, 1 }), // 19
				Arguments.of(10, new int[] { 10, 7, 1, 1, 1 }), // 20
				Arguments.of(20, new int[] { 10, 7, 2, 1, 1 }), // 21
				Arguments.of(20, new int[] { 10, 7, 1, 3, 1 }), // 22
				Arguments.of(20, new int[] { 20, 20, 7, 1, 1 }), // 49
				Arguments.of(20, new int[] { 20, 20, 8, 1, 1 }), // 50
				Arguments.of(30, new int[] { 23, 20, 6, 1, 1 }), // 51
				Arguments.of(30, new int[] { 24, 16, 10, 1, 1 }),// 52
				Arguments.of(30, new int[] { 23, 24, 24, 24, 24 }), // 119
				Arguments.of(30, new int[] { 24, 24, 24, 24, 24 })// 120

		);
	}

	// TC#1 Invalid values for number of hours
	@ParameterizedTest(name = "hours: {0}")
	@ValueSource(ints = { -2, -1, 25, 26 })
	void testInValidDailyValues(int hours) {
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			carParkFee.calculateDailyFee(hours);
		});
		assertEquals("Invalid hours reading " + hours + " received", exception.getMessage());
	}

	// TC#2 valid values
	@ParameterizedTest(name = "fee: {1} hours: {0}")
	@CsvFileSource(resources = "/car-data.csv", numLinesToSkip = 1)
	void testValidDailyValues(int hours, double fee) {
		assertEquals(fee, carParkFee.calculateDailyFee(hours));
	}

	// TC#3 hours out of range weekly
	@ParameterizedTest(name = "Hours has out of range value = {0}")
	@MethodSource("hoursOutOfRange")
	void testInValidWeeklyValues(int outOfRange, int[] hours) {
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			carParkFee.calculateWeeklyFee(hours);
		});
		assertEquals("Invalid hours reading " + outOfRange + " received", exception.getMessage());
	}

	// TC#4 number of values out of range weekly
	@ParameterizedTest(name = "{0} should have invalid number of marks")
	@MethodSource("invalidNumMarksValues")
	void invalidNumMarksTest(int numVal, int[] hours) {
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			carParkFee.calculateWeeklyFee(hours);
		});
		assertEquals("Invalid number of days " + numVal, exception.getMessage());

	}

	// TC#5 hours in range weekly
	@ParameterizedTest(name = "Hours{2} should result in {1}")
	@MethodSource("hoursInRange")
	void testValidWeeklyValues(double fee, int[] hours) {
		assertEquals(fee, carParkFee.calculateWeeklyFee(hours));
	}
	
	
	
	

}

