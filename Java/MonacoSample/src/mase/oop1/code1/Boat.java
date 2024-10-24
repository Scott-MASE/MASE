package mase.oop1.code1;

// SK
public abstract class Boat implements Machine {

    protected double thePrice;

    public Boat(double aPrice) {
        thePrice = aPrice;
    }

    @Override
    public double getPrice() {
        return thePrice;
    }

    @Override
    public String toString() {
//        return getPrice() + " " + this.getClass().getSimpleName();
        return this.getClass().getSimpleName();
    }
}

class Yacht extends Boat implements Desirable {

    Yacht(double aPrice) {
        super(aPrice);
    }

    @Override
    public void start() {
        System.out.println("Yacht::start");
    }

    @Override
    public void stop() {
        System.out.println("Yacht::stop");
    }
}

class Canoe extends Boat {

    Canoe(double aPrice) {
        super(aPrice);
    }

    @Override
    public void start() {
        System.out.println("Canoe::start");
    }

    @Override
    public void stop() {
        System.out.println("Canoe::stop");
    }
}

class Kayak extends Boat {

    Kayak(double aPrice) {
        super(aPrice);
    }

    @Override
    public void start() {
        System.out.println("Kayak::start");
    }

    @Override
    public void stop() {
        System.out.println("Kayak::stop");
    }
}
