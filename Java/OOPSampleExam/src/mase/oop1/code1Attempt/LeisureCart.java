package mase.oop1.code1Attempt;

import java.util.Date;


public final class LeisureCart {
	private String location;
	private Date dateOfPurchase;
	private Machine machine;
	
	private LeisureCart(String loc, Date date, Machine aMachine) {
		this.location = loc;
		this.dateOfPurchase = date;
		
		if (aMachine instanceof Yacht) {
			this.machine = new Yacht(aMachine.getPrice());
		}
	     else if (aMachine instanceof Kayak) {
	        this.machine = new Kayak(aMachine.getPrice());
	    } else if (aMachine instanceof Canoe) {
	        this.machine = new Canoe(aMachine.getPrice());
	    } else if (aMachine instanceof Saloon) {
	        this.machine = new Saloon(aMachine.getPrice());
	    } else if (aMachine instanceof Hatchback) {
	        this.machine = new Hatchback(aMachine.getPrice());
	    } else if (aMachine instanceof Convertible) {
	        this.machine = new Convertible(aMachine.getPrice());
	    } else {
	        this.machine = null;
	    }
	}
	
    public static LeisureCart createNewInstance(String location, Date dateOfPurchase, Machine thePurchasedItem) {
        return new LeisureCart(location, dateOfPurchase, thePurchasedItem);
    }
	
	public String getLocation() {
		return location;
	}
	public Date getDateOfPurchase() {
		return dateOfPurchase;
	}

	
    public Machine getMachine() {
        if (machine instanceof Yacht) {
            return new Yacht(machine.getPrice());
        } else if (machine instanceof Kayak) {
            return new Kayak(machine.getPrice());
        } else if (machine instanceof Canoe) {
            return new Canoe(machine.getPrice());
        } else if (machine instanceof Saloon) {
            return new Saloon(machine.getPrice());
        } else if (machine instanceof Hatchback) {
            return new Hatchback(machine.getPrice());
        } else if (machine instanceof Convertible) {
            return new Convertible(machine.getPrice());
        }

        // Hopefully don't get here...
        return null;
    }

	@Override
	public String toString() {
		return "Where?: " + this.location + " How much?: " + this.machine.getPrice() + " When?: " + this.dateOfPurchase + "\nDetails: " + this.machine.toString(); 
	}
	
	
	
	

}
