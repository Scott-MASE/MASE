package innerclasses;

public class Outer {

    private int privateOuterData = 20;

    class Inner {

        private int privateInnerData = 10;

        private void innerDisplay() {
            // Inner class has access to private Inner data (obviously)    	
            //System.out.println("Inner accessing Inner private data == "+privateInnerData);// 10

            // **Inner class has access to private Outer data**    	
            System.out.println("Inner has access to private Outer data == "
                    + privateOuterData);// 20
        }
    }

    private void outerDisplay() {
//		System.out.println("Inner : "+privateInnerData);// error - unknown variable
        // Need to use "new Inner()"
        System.out.println("Outer access to Inner private data == "
                + new Inner().privateInnerData);// 10
    }

    public static void main(String... args) {
        //this.outerDisplay();
        Outer outerRef = new Outer();
//        Outer.Inner innerRef = new Outer().new Inner();
        Outer.Inner innerRef = outerRef.new Inner();

        //Outer class has access to private Outer data (obviously) 
        System.out.println("Outer accessing Outer private data == "
                + outerRef.privateOuterData); // 20

        // **Outer class has access to private Inner data**    	
        System.out.println("Outer accessing Inner private data == "
                + innerRef.privateInnerData); // 10

        outerRef.outerDisplay();// 10
        innerRef.innerDisplay();// 20
    }
}
