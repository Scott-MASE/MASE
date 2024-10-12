package enums.simple;

public enum Direction {
    
    // the instances (constructor calls also)
    NORTH("in a northerly direction..."),
    SOUTH("in a southerly direction..."),
    EAST("in an easterly direction..."),
    WEST("in a westerly direction...");
    
    String direction;
    Direction(String direction){
        this.direction = direction;
    }
    @Override
    public String toString(){
        return "We are travelling : "+direction;
    }
}