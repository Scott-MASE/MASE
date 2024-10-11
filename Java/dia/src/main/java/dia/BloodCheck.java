package dia;

public class BloodCheck {
	static final double LOW_SUGAR = 5.0;
	static final double OK_SUGAR = 7.0;
	static final double MAX_SUGAR = 10.0;
	static final double MIN_LEVEL=0.0;
	static final double MAX_LEVEL=20.0;
	static final int MIN_NUM_READINGS=3;
	static final int MAX_NUM_READINGS=6;
	static final double TYPE2_DIABETES_LIMIT=10;
	
	public String checkBloodSugarLevel(double sugarLevel) {
		if (sugarLevel < MIN_LEVEL || sugarLevel > MAX_LEVEL) {
			throw new IllegalArgumentException("Invalid sugar reading " + sugarLevel + " received");
		}
		String sugarLevelResult="";
		if (sugarLevel<=LOW_SUGAR) {
			sugarLevelResult="LOW";
		}
		else if (sugarLevel<=OK_SUGAR){
			sugarLevelResult="OK";
		}
		else if (sugarLevel<=MAX_SUGAR){
			sugarLevelResult="HIGH";
		}
		else {
			sugarLevelResult="ALERT";
		}
		return sugarLevelResult;	
	}
	
	public boolean checkBloodSugarForDiabetesType2(double[] sugarLevels) {
		if (sugarLevels.length < MIN_NUM_READINGS || sugarLevels.length > MAX_NUM_READINGS) {
			throw new IllegalArgumentException("Invalid number of readings "+sugarLevels.length);
		}
		for (double sugarLevel : sugarLevels) {
			if (sugarLevel < MIN_LEVEL || sugarLevel > MAX_LEVEL) {
				throw new IllegalArgumentException("Invalid sugar reading " + sugarLevel + " received");
			}
		}
		double totalLevel=0;
		double averageLevel=0;
		for (int i=0; i < sugarLevels.length; i++) {
		  totalLevel+=sugarLevels[i];
		}
		boolean hasType2Diabetes=false;
		averageLevel=totalLevel/sugarLevels.length;
		if (averageLevel>TYPE2_DIABETES_LIMIT) {
			return true;
		}
		return hasType2Diabetes;
	}
}
