package mase.oop1.code1;

public abstract class Cat implements WildAnimal {
	private String theName;
	
	public Cat(String aName) {
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

class Tiger extends Cat implements ProtectedSpecies{
	
	Tiger(String aName){
		super(aName);
	}
}

class Lion extends Cat{
	
	Lion(String aName){
		super(aName);
	}
}
