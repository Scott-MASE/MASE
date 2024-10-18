package com.ait.myBlood;

public class BloodCheck {

	public String checkBloodSugarLevels(Double level) {
		String result = "ALERT";
		if (level < 1 || level > 20) {
			throw new IllegalArgumentException("Level must be between 0 and 20. value given outside range: " + level);
		}
		if (level <= 5.0) {
			result = "LOW";
		}
		else if (level <= 7) {
			result = "OK";
		}
		else if (level <= 10) {
			result = "HIGH";
		}
		
		return result;
	}
	
	public boolean checkDiabetes(double[] levels) {
		double avg = 0;
		if (levels.length > 6 || levels.length < 3) {
			throw new IllegalArgumentException("Must provide between 3 and 6 values. values provided: " + levels.length);
		}
		for (double i: levels) {
			avg += i;
		}
		avg = avg/levels.length;
		
		return (avg > 10);
		
	}
	
}
