package com.ait.myInsurance;

public class main {

	public static void main(String[] args) {
		CalculateInsurancePremium pre = new CalculateInsurancePremium();
		double fa = pre.calculatePremium(22,true, new int[] {4510,50,100});
		System.out.println(fa);
	}

}
