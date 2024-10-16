package com.ait.myInsurance;

import java.text.DecimalFormat;

public class CalculateInsurancePremium {
	
	public double calculatePremium(int age, boolean hasAccidents, int[] carValues) {
		final int minAge = 18;
		final int maxAge = 95;
		final int maxVehicles = 3;
		final int youngDriverThreshold = 25;
		final int adultDriverThreshold = 60;
		final double accidentRate = 0.2;
		
		double premium = 0;
		double rate = 0.05;
		
		if (age < minAge || age > maxAge) {
			throw new IllegalArgumentException("Age must be in range 18-90: " + age + " not allowed");
		}
		
		if (carValues.length < 1 || carValues.length > 3) {
			throw new IllegalArgumentException(carValues.length + " car values provided, should be one to three");
		}
		
		if (age <= youngDriverThreshold) {
			rate *= 1.5;
		}
		else if (age > adultDriverThreshold) {
			rate *= 1.25;
		}
		if (hasAccidents) {
			rate *= 1.2;
		}
		
		for (int carValue: carValues){
			premium += carValue * rate;
		}
		
		DecimalFormat formatter = new DecimalFormat("##.00");
		return Double.valueOf(formatter.format(premium));
		
	}

}
