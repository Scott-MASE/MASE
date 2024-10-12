package com.ait.parking;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class CarParkFeeTest {
	
	private CarParkFee cpf;

	@BeforeEach
	public void setup() throws Exception {
		cpf = new CarParkFee(); 
	}
	
	@ParameterizedTest(name="fee: {0} hours cost {1}")
	@CsvFileSource(resources="/car_data.csv", numLinesToSkip = 1)
	void testValidHours(String hoursStr, double expectedResult) {
	    // Convert the comma-separated string into an int array
	    int[] hours = convertToIntArray(hoursStr);
	    
	    double result = cpf.calculateWeeklyFee(hours);
	    assertEquals(expectedResult, result);
	}

	// Helper method to convert a comma-separated string into an int array
	private int[] convertToIntArray(String hoursStr) {
	    return Arrays.stream(hoursStr.split(","))
	                 .mapToInt(Integer::parseInt)
	                 .toArray();
	}

}
