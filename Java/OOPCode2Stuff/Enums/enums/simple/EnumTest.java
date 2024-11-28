package enums.simple;

public class EnumTest {
    Day day;

    public EnumTest(Day day) {
        this.day = day;
    }

    public static void daytype() {
        for (Day d : Day.values()) {
            System.out.println(d);
        }
    }

    public void tellItLikeItIt() { // instance method
        switch (day) {
            case MONDAY:
                System.out.println("Mondays are bad!");
                break;
            case FRIDAY:
                System.out.println("Fridays are better!");
                break;
            case SATURDAY:
            case SUNDAY:
                System.out.println("Weekends are best!");
                break;
            default:
                System.out.println("Midweeks are so-so!");
                break;
        }
    }

    public static void main(String[] args) {
        // EnumTest monday = new EnumTest(Day.MONDAY);
        // monday.tellItLikeItIt();
        // EnumTest wed = new EnumTest(Day.WEDNESDAY);
        // wed.tellItLikeItIt();
        // EnumTest fri = new EnumTest(Day.FRIDAY);
        // fri.tellItLikeItIt();
        // EnumTest sat = new EnumTest(Day.SATURDAY);
        // sat.tellItLikeItIt();
        // EnumTest sun = new EnumTest(Day.SUNDAY);
        // sun.tellItLikeItIt();
        daytype();
    }
}