package mase.oop1.code1;

public abstract class Ape implements WildAnimal {
	private String theName;
	
	public Ape(String aName) {
		this.theName = aName;
	}
	
    @Override
    public String getName() {
        return theName;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
	
	
}

class Gorilla extends Ape implements ProtectedSpecies{
	
	Gorilla(String aName){
		super(aName);
	}
}

class Chimpanzee extends Ape{
	
	Chimpanzee(String aName){
		super(aName);
	}
}
