package mase.oop1.code1Attempt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.DateFormat;

public class Monaco {

	public static void main(String[] args) {
		try {
		ArrayList<Billionaire> people = new ArrayList<>();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		
		Date date = formatter.parse("07/09/17");
		LeisureCart cartSheik = LeisureCart.createNewInstance("Monaco", date, new Yacht(25000000.0));
		Billionaire sheik = new Billionaire("Sheik Hammad", cartSheik);
		people.add(sheik);
		
		
		date  = formatter.parse("11/09/17");
		LeisureCart cartJP = LeisureCart.createNewInstance("Ireland", date, new Saloon(25000.0));
		Billionaire jp = new Billionaire("JP", cartJP);
		people.add(jp);
		
		processBillionaires(people);
		
		
		
		} catch(ParseException ex) {
			Logger.getLogger(Billionaire.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void processBillionaires(ArrayList<Billionaire> people) {
		for (Billionaire i : people) {
			System.out.print(i);
			if (i.getPurchase().getMachine() instanceof Desirable) {
				System.out.println("Desirable");
			}
			if (i.getPurchase().getMachine() instanceof Car) {
				Car car = (Car) i.getPurchase().getMachine();
				if (car.isPractical()) {
					System.out.print(" - Practical car");
				}
				else {
					System.out.print(" - Not practical car");
				}
			}
		}

	}

}

class Billionaire {
	private String name;
	private LeisureCart thePurchase;

	Billionaire(String n, LeisureCart p) {
		this.name = n;
		this.thePurchase = p;
	}

	public LeisureCart getPurchase() {
		return thePurchase;
	}

	@Override
	public String toString() {
		return  this.name + " Purchased " + thePurchase.toString();
	}

}
