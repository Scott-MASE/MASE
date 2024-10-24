package mase.oop1.code1;

import java.util.Date;

/**
 *
 * @author skennedy
 */
public final class LeisureCart {

    private final String location;	// String is immutable - no setters to change its content
    private final Date dateOfPurchase;	// Date is mutable - setters exist to change various date/time parts
    private final Machine machine;      // mutable

    //Default private constructor will ensure no unplanned construction of class
    private LeisureCart(String location, Date dateOfPurchase, Machine aMachine) {
        this.location = location;
        // don't store the reference passed in as the external class can change 
        // what you are looking at then; instead, copy the content of the Date 
        // passed in into a *new* Date object and store that new reference 
        this.dateOfPurchase = new Date(dateOfPurchase.getTime());
        
        if (aMachine instanceof Yacht) {
            this.machine = new Yacht(aMachine.getPrice());
        } else if (aMachine instanceof Kayak) {
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

    // Factory method (static) to store object creation logic in single place.
    // Enables external classes to create instances of ImmutableClass. 
    public static LeisureCart createNewInstance(String location, Date dateOfPurchase, Machine thePurchasedItem) {
        return new LeisureCart(location, dateOfPurchase, thePurchasedItem);
    }

    //Provide no setter methods
    public String getLocation() {	// String class is immutable so we can return the instance variable as it is
        return location;
    }

    /**
     * Date class is mutable so we need a little care here. We should not return
     * the reference of original instance variable as this would enable external
     * classes to change the state of the Date object we stored in out immutable
     * class... Instead a new Date object, with content copied to it, should be
     * returned. Thus, a copy of our Date object, along with a new reference is
     * created. An external class that changes that Date object is obviously not
     * changing our internal one.
     *
     */
    public Date getDate() {// mutable
        return new Date(dateOfPurchase.getTime());
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
//        return "Where?:\t" + location +"\tHow much?:"+ price +"\t\tWhen?:\t"+ dateOfPurchase + "\tDetails:" + getDevice().toString();
        return "\tWhere?:\t" + location + "\tHow much?:" + machine.getPrice() + "\t\tWhen?:\t" + dateOfPurchase + "\n\t\tDetails:" + machine.toString();
    }
}
