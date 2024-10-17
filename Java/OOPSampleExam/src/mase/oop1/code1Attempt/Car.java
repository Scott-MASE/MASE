package mase.oop1.code1Attempt;

public abstract class Car implements Machine {
	protected double thePrice;
	
	public Car(double price) {
		this.thePrice = price;
	}
	
	@Override
	public double getPrice() {
		return this.thePrice;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
	
	public abstract boolean isPractical();

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}

class Saloon extends Car{

	public Saloon(double price) {
		super(price);
	}
	
	@Override
	public void start() {
		System.out.println("Saloon::Start");
	}
	@Override
	public void stop() {
		System.out.println("Saloon::Stop");
	}
	
	@Override
	public boolean isPractical() {
		return true;
	}
	
	
}

class Hatchback extends Car{

	public Hatchback(double price) {
		super(price);
	}
	
	@Override
	public void start() {
		System.out.println("Hatchback::Start");
	}
	@Override
	public void stop() {
		System.out.println("Hatchback::Stop");
	}
	
	@Override
	public boolean isPractical() {
		return true;
	}
	
}

class Convertible extends Car implements Machine{

	public Convertible(double price) {
		super(price);
	}
	
	@Override
	public void start() {
		System.out.println("Convertible::Start");
	}
	@Override
	public void stop() {
		System.out.println("Convertible::Stop");
	}
	
	@Override
	public boolean isPractical() {
		return false;
	}
	
}
