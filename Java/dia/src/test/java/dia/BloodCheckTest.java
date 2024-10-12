package dia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;


public class BloodCheckTest {
	
	private BloodCheck bloodCheck;

	@BeforeEach
	public void setUp() throws Exception {
		bloodCheck = new BloodCheck();
	}
	static Stream<Arguments> invalidNumValuesDiabetes() {
		 return Stream.of(
				 Arguments.of( 1, new double[] { 4 } ),
				 Arguments.of( 2,new double[] { 5, 3 } ),
				 Arguments.of( 7, new double[] { 3, 3, 5, 4, 4, 4,8 } ),
				 Arguments.of( 8, new double[] { 5, 3, 3, 4, 4, 6, 7,5 } )
		  );
	} 
	
	static Stream<Arguments> invalidValuesDiabetes() {
		 return Stream.of(
				 Arguments.of( -0.2, new double[] { -0.2, 4, 4,5,6 } ),
				 Arguments.of( -0.1,new double[] { 5, -0.1, 3, 4,10} ),
				 Arguments.of( 20.1, new double[] { 3, 3, 5, 4, 20.1 } ),
				 Arguments.of( 20.2, new double[] { 5, 3, 3, 4, 20.2 } )
		  );
	} 
	
	static Stream<Arguments> validValuesAndNumDiabetes() {
		 return Stream.of(
				 Arguments.of( false, new double[] { 0.0, 0, 0 } ),
				 Arguments.of( false, new double[] { 0.1, 0.1, 0.1,0.1 } ),
				 Arguments.of( false, new double[] { 9.9,9.9, 9.9, 9.9,9.9} ),
				 Arguments.of( false, new double[] { 15, 5, 10, 10, 10.0,10.0 } ), //avg 10
				 Arguments.of( true, new double[] { 10.1, 10.1, 10.1, 10.1, 10.1 } ),
				 Arguments.of( true, new double[] { 10.2, 10.2, 10.2, 10.2, 10.2 } ),
				 Arguments.of( true, new double[] { 19.9, 19.9, 19.9, 19.9, 19.9 } ),
				 Arguments.of( true, new double[] { 20.0, 20.0, 20.0, 20.0, 20.0 } )
		  );
	} 
	//Invalid values test one reading
	@ParameterizedTest(name="sugar level: {0} is invalid")
	@ValueSource(doubles = { -0.2, -0.1, 20.1, 20.2 })
	void testInValidSugarLevels(double sugarLevel) {
		Throwable exception=assertThrows(IllegalArgumentException.class,()->{bloodCheck.checkBloodSugarLevel(sugarLevel);});
		assertEquals("Invalid sugar reading " + sugarLevel + " received",exception.getMessage());
	}
	//Valid values and results
	@ParameterizedTest(name="sugar level: {0} is {1}")
	@CsvFileSource(resources="/sugar-data.csv")
	void testValidSugarLevels(double sugarLevel,String expectedResult) {
		String result=bloodCheck.checkBloodSugarLevel(sugarLevel);
		assertEquals(expectedResult,result);
	}
	//invalid values diabetes test
	@ParameterizedTest(name="sugar level: {0} is invalid")
	@MethodSource("invalidValuesDiabetes")
	void testInValidSugarLevelsDiabetes(double invalidSugarLevel,double[] sugarLevels) {
		Throwable exception=assertThrows(IllegalArgumentException.class,()->{bloodCheck.checkBloodSugarForDiabetesType2(sugarLevels);});
		assertEquals("Invalid sugar reading " + invalidSugarLevel + " received",exception.getMessage());
	}
	//invalid number of readings
	@ParameterizedTest(name = "{1} should have invalid number of values")
	@MethodSource("invalidNumValuesDiabetes")
	public void invalidNumSugarLevelsDiabetesTest(int numValues, double[] bloodSugarReadings) {
		Throwable exception=assertThrows(IllegalArgumentException.class, () -> {bloodCheck.checkBloodSugarForDiabetesType2(bloodSugarReadings);});
		assertEquals("Invalid number of readings "+numValues,exception.getMessage());

	}
	//valid values
	@ParameterizedTest(name = "{1} should have invalid number of values")
	@MethodSource("validValuesAndNumDiabetes")
	public void validSugarLevelsDiabetesTest(boolean result, double[] bloodSugarReadings) {
		assertEquals(result,bloodCheck.checkBloodSugarForDiabetesType2(bloodSugarReadings));
	}

}
