//package com.ait.myInsurance;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.ValueSource;
//
//import com.ait.insurance.CalculateInsurancePremium;
//
//class CalculateInsurancePremium {
//	
//	private static final int[] CAR_VALUES = {200,30,10000};
//	
//	CalculateInsurancePremium cip;
//
//	@BeforeEach
//	void setUp() throws Exception {
//		CalculateInsurancePremium cip = new CalculateInsurancePremium();
//	}
//
//	@ParameterizedTest(name = "Age must be in range 19-85: {0} not allowed")
//	@ValueSource(ints =  {16,17,96,97})
//	void testAgeInvalid(int age) {
//		Throwable exception=assertThrows(IllegalArgumentException.class,()->{cip.calculatePremium(age, false, CAR_VALUES);});
//	}
//
//}
