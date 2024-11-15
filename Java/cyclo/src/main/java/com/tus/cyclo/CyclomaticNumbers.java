package com.tus.cyclo;

public class CyclomaticNumbers {
	
	public void howComplex(int val) {
		int i=val;
			if (i%2==0) {
				System.out.println("even");
			} else {
				System.out.println("odd");
			}
	}
	
	public String category(int age) {
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
	
	public String categorySimpler(int age) {
		if (age<17) {
			return "you are too young to drive";
		}
		else if (age <25) {
			return "your insurance will be expensive";
		}
		else if (age <30) {
			return "your insurance will be getting cheaper";
		}
		else if (age <65) {
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
	 public static String evaluateNumber(int num) {
	        if (num < 0) {
	            return "Negative";
	        } else if (num == 0) {
	            return "Zero";
	        } else if (num<= 10) {
	            return "Positive and Small";
	        } else if (num <= 100) {
	            return "Positive and Medium";
	        } else {
	            return "Positive and Large";
	        }
	    }
	

	public static String determineTriangle(int a, int b, int c) {
		if (a <= 0 || b <= 0 || c <= 0) {
			return "Invalid";
		}
		if (a + b <= c || a + c <= b || b + c <= a) {
			return "Not a triangle";
		}
		if (a == b && b == c) {
			return "Equilateral";
		} else if (a == b || a == c || b == c) {
			return "Isosceles";
		} else {
			return "Scalene";
		}
	}
	
	 public static String analyzeNumber(int n) {
	        if (n < 0) {
	            return "Negative";
	        } else if (n == 0) {
	            return "Zero";
	        } else if (n > 0 && n < 10) {
	            return "Single Digit Positive";
	        } else if (n % 2 == 0) {
	            return "Positive Even";
	        } else {
	            return "Positive Odd";
	        }
	    }

}
