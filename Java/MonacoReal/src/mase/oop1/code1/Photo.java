package mase.oop1.code1;

public final class Photo {
	
	private final String location;
	private final WildAnimal wildAnimal;
	
	
	private Photo(String location, WildAnimal wildAnimal) {
		this.location = location;
		if (wildAnimal instanceof Gorilla) {
			this.wildAnimal = new Gorilla(wildAnimal.getName());
		}
		else if (wildAnimal instanceof Chimpanzee) {
			this.wildAnimal = new Chimpanzee(wildAnimal.getName());
		}
		else if (wildAnimal instanceof Lion) {
			this.wildAnimal = new Lion(wildAnimal.getName());
		}
		else if (wildAnimal instanceof Tiger) {
			this.wildAnimal = new Tiger(wildAnimal.getName());
		}
		else {
			this.wildAnimal = null;
		}
	}
	
	public static Photo createNewInstance(String location, WildAnimal theAnimal) {
		return new Photo(location, theAnimal);
	}
	
    public String getLocation() {	
        return location;
    }
    
    public WildAnimal getWildAnimal() {
		if (wildAnimal instanceof Gorilla) {
			return new Gorilla(wildAnimal.getName());
		}
		else if (wildAnimal instanceof Chimpanzee) {
			return new Chimpanzee(wildAnimal.getName());
		}
		else if (wildAnimal instanceof Lion) {
			return new Lion(wildAnimal.getName());
		}
		else if (wildAnimal instanceof Tiger) {
			return new Tiger(wildAnimal.getName());
		}
		else {
			return null;
		}
    }
    
    @Override
    public String toString() {
    	return "\tLocation is:\t\t" + location + "\n\tAnimal is: " + wildAnimal.toString() + "\t\tName is: " + wildAnimal.getName();
    }

	
	
}
