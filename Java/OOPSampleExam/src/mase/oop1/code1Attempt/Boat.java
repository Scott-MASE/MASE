package mase.oop1.code1Attempt;

public abstract class Boat implements Machine {
	protected double thePrice;
	
	
	public Boat(double price){
		this.thePrice = price;
	}

	@Override
	public double getPrice() {
		return thePrice;
	}


	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
	

class Yacht extends Boat implements Desirable{

	Yacht(double price) {
		super(price);
	}
	
	@Override
	public void start() {
		System.out.println("Yacht::Start");
	}
	@Override
	public void stop() {
		System.out.println("Yacht::Stop");
	}
	
	
}

class Canoe extends Boat{

	Canoe(double price) {
		super(price);
	}
	@Override
	public void start() {
		System.out.println("Canoe::Start");
	}
	@Override
	public void stop() {
		System.out.println("Canoe::Stop");
	}
	
	
}

class Kayak extends Boat{

	Kayak(double price) {
		super(price);
	}
	@Override
	public void start() {
		System.out.println("Kayak::Start");
	}
	@Override
	public void stop() {
		System.out.println("Kayak::Stop");
	}
	
	
}


