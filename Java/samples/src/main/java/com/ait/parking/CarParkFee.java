package com.ait.parking;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CarParkFee {
	
	public double calculateDailyFee(int numHours) {
		double fee = 0;
		
	    if (numHours < 1 || numHours > 24) {
	        throw new IllegalArgumentException("Invalid value entered: " + numHours);
	    }
		
		if (numHours < 6) {
			fee = numHours * 2;
		}
		else if (numHours < 11) {
			fee = (numHours * 2) * 0.8;
		}
		else if (numHours < 25) {
			fee = 20;
		}
		
		System.out.println(Math.round(fee));
		return Math.round(fee);
	}
	
	public double calculateWeeklyFee(int[] hours) {
		double fee;
		int sum = 0;
		for (int i: hours) {
			sum += i;
			}
		
		if (sum < 21 && sum > 0) {
			fee = 10;
			}
		else if(sum < 51) {
			fee = 20;
			}
		else {
			fee = 30;
			}	
		return fee;
		
		}

		
	}


