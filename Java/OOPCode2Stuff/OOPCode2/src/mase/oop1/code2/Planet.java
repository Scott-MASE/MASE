package mase.oop1.code2;

public enum Planet {
	MERCURY(57_000_000.9,0),
	VENUS(108_000_000.2,0),
	EARTH(149_000_000.6,1),
	MARS(227_000_000.9,2),
	JUPITER(778_000_000.3,67),
	SATURN(1_427_000_000.0,62),
	URANUS(2_871_000_000.0,27),
	NEPTUNE(4_497_000_000.1,13),
	PLUTO(5_913_000_000.0,4);
	
	double distanceFromTheSun;
	int numberOfMoons;
	
	Planet(double distanceFromTheSun, int numberOfMoons){
		this.distanceFromTheSun = distanceFromTheSun;
		this.numberOfMoons = numberOfMoons;
	}
	
	public double getDistanceFromSun() {
		return distanceFromTheSun;
	}
	
	public int getNumberOfMoons() {
		return numberOfMoons;
	}
	
    @Override
    public String toString() {
        return name() + " is " + distanceFromTheSun + " kms from the Sun and has " + numberOfMoons + " moons.";
    }

}
