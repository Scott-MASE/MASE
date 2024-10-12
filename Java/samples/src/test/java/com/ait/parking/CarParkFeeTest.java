package com.ait.parking;

import static org.junit.jupiter.api.Assertions.*;

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
	@CsvFileSource(resources="/car_data.csv")
	void testValidHours(int[] hours,double expectedResult) {
		
		double result=cpf.calculateWeeklyFee(hours);
		assertEquals(expectedResult,result);
	}


}
