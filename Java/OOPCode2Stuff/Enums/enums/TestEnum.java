package enums;

public class TestEnum {

    public static void main(String[] args) {
        enums();
    }

    public static void enums() {
        Farmer john = Farmer.BEEF;
        // john is after buying some land...
        john.setNumAcres(500);
        double grant = calculateGrant(john);

        // display the value of the toString method of that farmer type
        System.out.println("John is a : " + john);
        System.out.println("John's grant is: " + grant);

    }

    public static double calculateGrant(Farmer farmer) {
        double grant = 0.0;
        switch (farmer) {
            case BEEF:
                grant = farmer.getNumAcres() < 200 ? 200 * farmer.getNumAcres()
                        : 50 * farmer.getNumAcres();
                break;
            case DAIRY:
                grant = farmer.getNumAcres() < 200 ? 300 * farmer.getNumAcres()
                        : 80 * farmer.getNumAcres();
                break;
            case SHEEP:
                grant = farmer.getNumAcres() < 200 ? 150 * farmer.getNumAcres()
                        : 40 * farmer.getNumAcres();
                break;
        }

        return grant;

    }
}
