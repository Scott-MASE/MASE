package com.tus.rates;

public class CalculateUtilityBill {
	final double SOLAR_PANEL_DISCOUNT = 0.95;

	final double ELEC_T1_RATE = 0.10;
	final double ELEC_T2_RATE = 0.12;
	final double ELEC_T3_RATE = 0.15;
	final int ELEC_T1_TOP = 100;
	final int ELEC_T2_TOP = 500;

	final double WATER_T1_RATE = 1.50;
	final double WATER_T2_RATE = 2.00;
	final double WATER_T3_RATE = 2.50;
	final int WATER_T1_TOP = 30;
	final int WATER_T2_TOP = 80;

	final double GAS_T1_RATE = 0.09;
	final double GAS_T2_RATE = 0.11;
	final double GAS_T3_RATE = 0.13;
	final int GAS_T1_TOP = 300;
	final int GAS_T2_TOP = 500;

	public double calculateBill(int[] usages, boolean hasSolarPanels) {
		
		if (usages.length != 3) {
			throw new IllegalArgumentException("Invalid number of usage values: expected 3, found " + usages.length);
		}
		
		final int ELECTRICITY_KWH = usages[0];
		final int WATER_M3 = usages[1];
		final int GAS_M3 = usages[2];

		double rate;


		String negativeQuantity = "";
		double totalBill;
		double finalBill;

		if (ELECTRICITY_KWH < 0) {
			negativeQuantity = ELECTRICITY_KWH + " KWh of electricity";
		} else if (WATER_M3 < 0) {
			negativeQuantity = WATER_M3 + " cubic meters of water";
		} else if (GAS_M3 < 0) {
			negativeQuantity = GAS_M3 + " cubic meters of gas";
		}
		
		if (negativeQuantity != "") {
			throw new IllegalArgumentException("Usage cannot be negative: " + negativeQuantity);
		}



		if (ELECTRICITY_KWH <= ELEC_T1_TOP) {
			rate = ELEC_T1_RATE * ELECTRICITY_KWH;
		} else if (ELECTRICITY_KWH <= ELEC_T2_TOP) {
			rate = (ELEC_T1_RATE * ELEC_T1_TOP) + (ELEC_T2_RATE * (ELECTRICITY_KWH - ELEC_T1_TOP));
		}
		else {
			rate = (ELEC_T1_RATE * ELEC_T1_TOP) + (ELEC_T2_RATE * (ELEC_T2_TOP-ELEC_T1_TOP)) + (ELEC_T3_RATE * (ELECTRICITY_KWH - ELEC_T2_TOP));
		}

		final double ELECTRICITY_BILL = rate;
		System.out.println(ELECTRICITY_BILL);

		if (WATER_M3 <= WATER_T1_TOP) {
			rate = WATER_T1_RATE * WATER_M3;
		} else if (WATER_M3 <= WATER_T2_TOP) {
			rate = (WATER_T1_RATE * WATER_T1_TOP) + (WATER_T2_RATE * (WATER_M3 - WATER_T1_TOP));
		}
		else {
			rate = (WATER_T1_RATE * WATER_T1_TOP) + (WATER_T2_RATE * (WATER_T2_TOP-WATER_T1_TOP)) + (WATER_T3_RATE * (WATER_M3 - WATER_T2_TOP));
		}

		final double WATER_BILL = rate;
		System.out.println(WATER_BILL);

		if (GAS_M3 <= GAS_T1_TOP) {
			rate = GAS_T1_RATE * GAS_M3;
		} else if (GAS_M3 <= GAS_T2_TOP) {
			rate = (GAS_T1_RATE * GAS_T1_TOP) + (GAS_T2_RATE * (GAS_M3 - GAS_T1_TOP));
		}
		else {
			rate = (GAS_T1_RATE * GAS_T1_TOP) + (GAS_T2_RATE * (GAS_T2_TOP-GAS_T1_TOP)) + (GAS_T3_RATE * (GAS_M3 - GAS_T2_TOP));
		}

		final double GAS_BILL = rate;
		System.out.println(GAS_BILL);


		totalBill = ELECTRICITY_BILL + WATER_BILL + GAS_BILL;
		if (hasSolarPanels) {
			finalBill = totalBill * SOLAR_PANEL_DISCOUNT;
		} else {
			finalBill = totalBill;
		}

		return finalBill;

	}

}
