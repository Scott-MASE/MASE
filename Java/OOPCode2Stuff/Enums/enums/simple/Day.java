package enums.simple;

// simple enum
public enum Day {
    SUNDAY("dread"), MONDAY("AHHH"), TUESDAY("ugh"), WEDNESDAY("ughhh"), THURSDAY("..."){@Override public String toString() { return "Pure fatigue courses through his veins"; }}, FRIDAY("yay"), SATURDAY("whoop");


    private String type;

    Day(String type) {
        this.type = type;
    };


    @Override
    public String toString() {
        return name();
        // return "The day is " + type;
    }
}
