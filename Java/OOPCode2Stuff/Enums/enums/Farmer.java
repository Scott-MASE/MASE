// Sean Kennedy
package enums;

public enum Farmer{
    DAIRY(200), // default values
    BEEF(150),
    SHEEP(160);
    
    private int numAcres;
    
    // the constructor cannot be public or protected
    /* Think of Enums as a class with a finite number of instances. 
       There can never be any different instances beside the ones you 
       initially declare. Thus, you cannot have a public or protected 
       constructor, because that would allow more instances to be created.
    */
    Farmer(int numAcres){ // is private by default
        this.numAcres=numAcres;
    }
    
    public int getNumAcres(){
        return numAcres;
    }
    public void setNumAcres(int numAcres){
        this.numAcres=numAcres;        
    }
    
    @Override
    public String toString(){
        final int DAIRY=0, BEEF=1, SHEEP=2;
        String s = "";
        
        switch (this.ordinal()) {
            case DAIRY:
                s = " Dairy farmer ";
                break;
            case BEEF:
                s = " Beef farmer ";
                break;
            case SHEEP:
                s = " Sheep farmer ";
                break;
            default:
                break;
        }
        return s + "and has " + numAcres + " acres.";
    }

}