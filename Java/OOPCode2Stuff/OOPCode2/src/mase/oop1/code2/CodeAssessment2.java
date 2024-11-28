// Name: Scott Daly
package mase.oop1.code2;

import java.util.Scanner;

public class CodeAssessment2 {

	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		final int INNER_CLASSES = 1, ENUMS = 2, EXCEPTIONS = 3, EXIT = 99;
		String userContinue = "y";

		while (userContinue.equalsIgnoreCase("y")) {
			switch (userChoice()) {
			case INNER_CLASSES:
				innerClasses();
				break;
			case ENUMS:
				enums();
				break;
			case EXCEPTIONS:
				someMethod();
				break;
			case EXIT:
				System.out.println("Exiting...");
				userContinue = "n";
				break;
			default:
				System.out.println("\nUnknown entry!");
				break;
			}
		}
	}

	public static void innerClasses() {
		// Inner Classes code goes here...
		System.out.println("Inner Classes...\n");
		BoxOfMatches boxOfMatches = new BoxOfMatches();
		MatchStick matchStick = boxOfMatches.buyBox("Cara", "Maguire&Patterson", true, 40);
		System.out.println(matchStick.matchHeadIngredients());
	}

	public static void enums() {
		// Enums code goes here...
		System.out.println("Enums...\n");
		for (Planet planet : Planet.values()) {
			System.out.print(planet);
			if (tooFarFromSun(planet)) {
				System.out.print(" - NOT habitable!");
			}
			System.out.println();

		}

	}

	public static void someMethod() throws TestException {
		// Exceptions code goes here...
		System.out.println("Exceptions...\n");
		TestException.method1();
	}

	public static int userChoice() {
		System.out.println("\nWhat do you want to do ?");
		System.out.println("1. Inner Classes");
		System.out.println("2. Enums");
		System.out.println("3. Exceptions");
		System.out.println("99. Exit");
		System.out.print("Enter choice --> ");
		return sc.nextInt();
	}

	public static boolean tooFarFromSun(Planet planet) {
		if (planet.getDistanceFromSun() > 200_000_000.0) {
			return true;
		} else {
			return false;
		}
	}
}