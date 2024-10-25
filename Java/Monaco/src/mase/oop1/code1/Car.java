package mase.oop1.code1;

/**
 *
 * @author skennedy
 */
public abstract class Car implements Machine {

    protected double thePrice;

    public Car(double aPrice) {
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

    public abstract boolean isPractical();

}

class Saloon extends Car {

    Saloon(double aPrice) {
        super(aPrice);
    }

    @Override
    public void start() {
        System.out.println("Saloon::start");
    }

    @Override
    public void stop() {
        System.out.println("Saloon::stop");
    }

    @Override
    public boolean isPractical() {
        return true;
    }

}

class Hatchback extends Car {

    Hatchback(double aPrice) {
        super(aPrice);
    }

    @Override
    public void start() {
        System.out.println("Hatchback::start");
    }

    @Override
    public void stop() {
        System.out.println("Hatchback::stop");
    }

    @Override
    public boolean isPractical() {
        return true;
    }

}

class Convertible extends Car implements Desirable {

    Convertible(double aPrice) {
        super(aPrice);
    }

    @Override
    public void start() {
        System.out.println("Convertible::start");
    }

    @Override
    public void stop() {
        System.out.println("Convertible::stop");
    }

    @Override
    public boolean isPractical() {
        return false;
    }

}
