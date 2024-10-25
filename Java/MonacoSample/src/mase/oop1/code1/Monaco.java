package mase.oop1.code1;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @authorskennedy
 */
class Billionaire {

    private String name;
    private LeisureCart thePurchase;

    Billionaire(String name, LeisureCart aPurchase) {
        this.name = name;
        this.thePurchase = aPurchase;
    }

    public LeisureCart getPurchase() {
        return thePurchase;
        
    }

    @Override
    public String toString() {
        String displayString = "";

        displayString = this.name + " purchased: " + thePurchase.toString();
        return displayString;
    }
}

public class Monaco {

    public static void main(String[] args) {
        try {
            ArrayList<Billionaire> people = new ArrayList<>();
            DateFormat formatter = new SimpleDateFormat("dd/MM/yy");

            Date date = formatter.parse("07/09/17");
            LeisureCart cartSheikhHammad = LeisureCart.createNewInstance("Monaco", date, new Yacht(250000000.0));
            Billionaire sheikhHammad = new Billionaire("Sheikh Hammad", cartSheikhHammad);
            //System.out.println(sheikhHammad);
            people.add(sheikhHammad);

            date = formatter.parse("11/09/17");
            LeisureCart cartJPMcManus = LeisureCart.createNewInstance("Ireland", date, new Saloon(20000.0));
            Billionaire jpMcManus = new Billionaire("JP McManus", cartJPMcManus);
            //System.out.println(jpMcManus);
            people.add(jpMcManus);

            processBillionaires(people);

        } catch (ParseException ex) {
            Logger.getLogger(Billionaire.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void processBillionaires(ArrayList<Billionaire> billionaires) {
        for (Billionaire billionaire : billionaires) {
            System.out.print(billionaire);
            if (billionaire.getPurchase().getMachine() instanceof Desirable) {
                System.out.println(" - Desirable Item");
            }
            if (billionaire.getPurchase().getMachine() instanceof Car) {
                Car car = (Car) billionaire.getPurchase().getMachine();
                if (car.isPractical()) {
                    System.out.println(" - Practical car");
                } else {
                    System.out.println(" - Not a Practical car");
                }
            }
            System.out.println();
        }
    }

}
