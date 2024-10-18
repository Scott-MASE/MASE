package com.ait.myBlood;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

class BloodCheckTest {

	@BeforeEach
	void setUp() {
		BloodCheck bc = new BloodCheck();
	}

	static Stream<Arguments> invalidBloodLevels() {
		return Stream.of(
				Arguments.of(2, new double[] { 1.0, 2.0 }), 
				Arguments.of(3, new double[] { 1.0, 2.0, 3.0 }),
				Arguments.of(6, new double[] { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0 }),
				Arguments.of(7, new double[] { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0 })

		);

	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
