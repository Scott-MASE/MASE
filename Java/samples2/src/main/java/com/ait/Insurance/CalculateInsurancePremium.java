package com.ait.Insurance;
import java.text.DecimalFormat;

public class CalculateInsurancePremium {

	private static final int MIN_AGE = 18;
	private static final int MAX_AGE = 95;
	private static final int MIN_CAR_VALUES = 1;
	private static final int MAX_CAR_VALUES = 3;

	private static final double RATE_ONE = 1.5;
	private static final double RATE_TWO = 1.25;
	private static final double ACCIDENT_RATE = 0.2;
	private static final double BASE_RATE = 0.05;
	
	public double calculatePremium(int age, boolean hasAccidents, int[] carValues){
		double premium;
		if (carValues.length < MIN_CAR_VALUES || carValues.length > MAX_CAR_VALUES) {
			throw new IllegalArgumentException(carValues.length + " car values provided, should be one to three");
		}

		if (age < MIN_AGE || age > MAX_AGE) {
			throw new IllegalArgumentException("Age must be in range 18-95: " + age + " not allowed");
		}

		// got the sum of total cars value
		int totalCarValue = carValues[0] + carValues[1] + carValues[2];
		premium = totalCarValue * BASE_RATE;

		// Adjust premium based on age
		if (age <= 25) {
		    premium *= RATE_ONE;
		} else if (age > 60) {
		    premium *= RATE_TWO;
		}

		// Apply accident rate adjustment if applicable
		if (hasAccidents) {
		    premium += premium * ACCIDENT_RATE;
		}

		// Format the result to two decimal places and return
		DecimalFormat formatter = new DecimalFormat("##.00");
		return Double.valueOf(formatter.format(premium));
	}

}