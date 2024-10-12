package enums.simple;

public class EnumTest {

	Day day;

	public EnumTest(Day day) {
		this.day = day;
	}

	public void tellItLikeItIs() {
		switch (day) {
		case MONDAY:
			System.out.println("Mondays are bad");
			break;
		case FRIDAY:
			System.out.println("Fridays are better");
			break;
		case SATURDAY:
		case SUNDAY:
			System.out.println("weekend are the best");
			break;
		default:
			System.out.println("midweeks are soso");
			break;

		}
	}
	
	public static void main(String[] args) {
		EnumTest monday = new EnumTest(Day.MONDAY);
		monday.tellItLikeItIs();
		EnumTest wed = new EnumTest(Day.WEDNSDAY);
		wed.tellItLikeItIs();
		EnumTest fri = new EnumTest(Day.FRIDAY);
		fri.tellItLikeItIs();
		EnumTest sat = new EnumTest(Day.SATURDAY);
		sat.tellItLikeItIs();
		EnumTest sun = new EnumTest(Day.SUNDAY);
		sun.tellItLikeItIs();
		
	}
		

}
