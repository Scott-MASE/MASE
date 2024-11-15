package com.tus.cyclo;

public class CycloExample {
	public void howComplex(int val) {
		int i=val;
			if (i%2==0) {
				System.out.println("even");
			} else {
				System.out.println("odd");
			}
	}
	public static String category(int age) {
		if (age<17) {
			return "you are too young to drive";
		}
		else if (age>=17 && age <25) {
			return "your insurance will be expensive";
		}
		else if (age>=25 && age <30) {
			return "your insurance will be getting cheaper";
		}
		else if (age>=30 && age <65) {
			return "your insurance will be ok";
		}
		else {
			return "you need a medical before I can say";
		}
	}
	
	public static String determineTriangle1(int a, int b, int c) {
		if (a <= 0 || b <= 0 || c <= 0) {
			return "Invalid";
		}
		return "ok";
	}

}
