package j8.lambdas;

import java.util.function.Predicate;
// interface Predicate<T>
//     boolean test(T t)

class Dog{
	private int age;
	private String name;
	private boolean hasFur;
	
	Dog(int age, String name, boolean hasFur) {
		this.age = age;
		this.name = name;
		this.hasFur = hasFur;
	}

	boolean isFurry() { 
		return hasFur;
	}
}

public class BasicLambdas {
    public static void main(String[] args) {
    	
    	
        BasicLambdas bl = new BasicLambdas();
        bl.predicate();

        System.out.println(check( 4,  (Integer n) -> {return n%2 == 0;} ) );//true
        System.out.println(check(4, n -> n%2 == 0));//true
        System.out.println(check(7, n -> n%2 == 0));//false

        System.out.println(check("Mr. Joe Bloggs", (String s) -> { return s.startsWith("Mr."); }      ));// true
        System.out.println(check("Mr. Joe Bloggs", s -> s.startsWith("Mr.")));// true
        System.out.println(check("Ms. Joe Bloggs", s -> s.startsWith("Mr.")));// false

    	// 2022 - Dog version
    	Dog d1 = new Dog(4, "Rex", true);
    	Dog d2 = new Dog(12, "Spot", false);
    	System.out.println(check(d1, (Dog d) -> { return d.isFurry(); }));
    	System.out.println(check(d2, (Dog d) -> { return d.isFurry(); }));
    	
    	System.out.println(check(d1, d -> d.isFurry() ));
    	System.out.println(check(d2, d -> d.isFurry() ));
    }
    public static <T> boolean check(T t, Predicate<T> lambda){
        return lambda.test(t);
    }
//    public static boolean check(Integer i, Predicate<Integer> lambda){
//        return lambda.test(i);
//    }

    public void predicate(){
        // pre-Java 8
        // anonymous inner class syntax
        Evaluate<Integer> anonInnerRef =
            new Evaluate<Integer>() {
                // this is the anon inner class code
                @Override
                public boolean checkIfNegative(Integer i){
                    return i < 0;
                }
            };
        System.out.println("Anon: "+anonInnerRef.checkIfNegative(-1));// true
        System.out.println("Anon: "+anonInnerRef.checkIfNegative(+1));// false
    
        // Java 8 - lambda expressions
        // Lambda expression depends upon a Functional Interface.
        // A Functional Interface is an interface with 1 abstract method.
//        Evaluate<Integer> lambda = i -> i < 0;
        Evaluate<Integer> lambda = i -> i < 0;
        System.out.println("Java 8: "+lambda.checkIfNegative(-1));// true
        System.out.println("Java 8: "+lambda.checkIfNegative(+1));// false
    
        // Java 8 - Predicate interface
        Predicate<Integer> predicate = i -> i < 0;
        System.out.println("Java 8 Predicate: "+predicate.test(-1));// true
        System.out.println("Java 8 Predicate: "+predicate.test(+1));// false
    }
}
