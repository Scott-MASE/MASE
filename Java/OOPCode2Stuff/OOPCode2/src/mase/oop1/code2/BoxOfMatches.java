package mase.oop1.code2;

public class BoxOfMatches {

	private class MatchStickImpl implements MatchStick {
		private String brand;
		private String manufacturer;
		private boolean dangerous;
		private int numberOfMatchSticks;
		
		private MatchStickImpl(String b, String m, boolean d, int n){
			this.brand = b;
			this.manufacturer = m;
			this.dangerous = d;
			this.numberOfMatchSticks = n;
		}

		@Override
		public String matchHeadIngredients() {
			return "Match heads consist of : Potassium, Sulphur and Powder";
		}

	}
	
	public MatchStick buyBox(String b, String m, boolean d, int n) {
		return new MatchStickImpl(b,m,d,n);
	}

}
