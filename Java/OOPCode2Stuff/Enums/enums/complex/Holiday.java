package enums.complex;

//import static com.example.enums.complex.Direction.*;

public class Holiday {
    public static void main(String[] args) {
//        goSomewhere(EAST);
        
        goSomewhere(Direction.NORTH);
        
        Direction south = Direction.SOUTH;
        goSomewhere(south);
        
//        Direction west = new Direction("West");
    }
    public static void goSomewhere(Direction d){
        System.out.println(d);
    }
}