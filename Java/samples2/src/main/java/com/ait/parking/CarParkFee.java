package com.ait.parking;


public class CarParkFee {

	private static final int MIN_HOURS = 0;
	private static final int MAX_HOURS = 24;
	private static final int NUMBER_OF_WORKING_DAYS=5;
	private static final double HOURLY_FEE=2.0;

	public double calculateDailyFee(int numHours) {
		if (numHours < MIN_HOURS || numHours > MAX_HOURS) {
			throw new IllegalArgumentException("Invalid hours reading " + numHours + " received");
		}
		return getDailyFee(numHours);
	}

	public double calculateWeeklyFee(int[] hoursPerDay) {
		if (hoursPerDay.length != NUMBER_OF_WORKING_DAYS) {
			throw new IllegalArgumentException("Invalid number of days "+hoursPerDay.length);
		}
		for (int daily : hoursPerDay) {
			if (daily < MIN_HOURS || daily > MAX_HOURS) {
				throw new IllegalArgumentException("Invalid hours reading " + daily + " received");
			}
		}
		return getWeeklyFee(hoursPerDay);
	}
	
	private double getDailyFee(int numHours) {
		double fee;
		if (numHours <= 5) {
			fee = numHours * HOURLY_FEE;
		} else if (numHours <= 10) {
			fee = ((numHours * HOURLY_FEE) * 0.8);
			if(fee<10) {
				fee=10;
			}
		} else {
			fee = 20.00;
		}
		fee = Math.round(fee*100.0)/100.0;
		return fee;
	}
	
	private double getWeeklyFee(int[] hoursPerDay) {
		int hoursForWeek = 0;
		double fee = 0;
		for (int daily : hoursPerDay) {
			hoursForWeek += daily;
		}
		if (hoursForWeek <= 20) {
			fee = 10;
		} else if (hoursForWeek <= 50) {
			fee = 20;
		} else {
			fee = 30;
		}
		return fee;
	}
}

