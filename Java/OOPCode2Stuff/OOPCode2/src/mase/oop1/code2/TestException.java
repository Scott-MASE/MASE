package mase.oop1.code2;

import java.io.IOException;

class TestException extends Exception {
	public TestException() {
	}

	public TestException(Throwable cause) {
		super(cause);
	}

	public static void method1() throws TestException {
		method2();
	}
	
	public static void method2() throws TestException {
		try {
			method3();
		} catch (TestException te) {
            System.out.println("Caught TestException...");
            System.out.println("The original exception was: "
                    + te.getCause().toString());
		}
	}
	
	public static void method3() throws TestException {
		try {
			throw new IOException("An I/O exception occured.");
		} catch (IOException ioe) {
			throw new TestException(ioe);
		}
	}
	
	

}
