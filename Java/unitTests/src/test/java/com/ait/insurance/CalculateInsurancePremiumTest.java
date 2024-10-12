package com.ait.insurance;
import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class CalculateInsurancePremiumTest {
	
	private static final int[] CAR_VALUES = {2000,3000,5000};
	
	CalculateInsurancePremium calculateInsurancePremium;
	

	@BeforeEach
	void setup() {
		calculateInsurancePremium = new CalculateInsurancePremium();
	}
	
	// Check Invalid Age, Use @ValueSource to generate values for invalid ages
	@ParameterizedTest(name="Age must be in range 18-95: {0} not allowed")
	@ValueSource(ints= {16,17,96,97})
	void testAgeInvalid(int age) {
		Throwable exception=assertThrows(IllegalArgumentException.class,()->{calculateInsurancePremium.calculatePremium(age, false, CAR_VALUES);});
		assertEquals("Age must be in range 18-95: " + age + " not allowed",exception.getMessage());
	}
	
	
	// create a method for Use @MethodSource to generate invalid number of car values 
	static Stream<Arguments> InvalidNumOfCarValues() {
		return Stream.of(
				 Arguments.of( 0, new int[] { }),
				 Arguments.of( 4, new int[] {1000,2000,3000,4000}),
				 Arguments.of( 5, new int[] {1000,2000,3000,4000,5000 })
		  );
		
	}
	
	// create a method for Use @MethodSource to generate invalid number of car values 
	@ParameterizedTest(name = "{0} car values provided, should be one to three")
	@MethodSource("InvalidNumOfCarValues")
	void testInvalidNumOfCarValues(int num,int[] carValues) {
		Throwable exception=assertThrows(IllegalArgumentException.class, () -> { calculateInsurancePremium.calculatePremium(20, false, carValues);});
		assertEquals(num + " car values provided, should be one to three",exception.getMessage());
	}
	
	
	//Valid values and results. Use a csv file to generate the valid values
	@ParameterizedTest(name="age-{0} , hasAccidents-{1}, result-{2}")
	@CsvFileSource(resources="/car-values-data.csv", numLinesToSkip = 1)
	void testValidCarValues(int age, boolean hasAccidents, double result) {
		assertEquals(result, calculateInsurancePremium.calculatePremium(age, hasAccidents, CAR_VALUES));
	}
	
}