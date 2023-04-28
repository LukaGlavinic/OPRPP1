package hr.fer.oprpp1.hw04.db;

public class ComparisonOperators implements IComparisonOperator{

	public static final IComparisonOperator LESS = (s1, s2) -> s1.compareTo(s2) < 0 ? true : false;
	public static final IComparisonOperator LESS_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) <= 0 ? true : false;
	public static final IComparisonOperator GREATER = (s1, s2) -> s1.compareTo(s2) > 0 ? true : false;
	public static final IComparisonOperator GREATER_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) >= 0 ? true : false;
	public static final IComparisonOperator EQUALS = (s1, s2) -> s1.compareTo(s2) == 0 ? true : false;
	public static final IComparisonOperator NOT_EQUALS = (s1, s2) -> s1.compareTo(s2) != 0 ? true : false;
	public static final IComparisonOperator LIKE = new IComparisonOperator() {
		@Override
		public boolean satisfied(String niz, String uzorak) throws Exception {
			char[] poljeZnakova1 = niz.toCharArray();
			char[] poljeZnakova2 = uzorak.toCharArray();
			int indexZvi = 0;
			int brojZvi = 0;
			for(int i = 0; i < poljeZnakova2.length; i++) {
				if(poljeZnakova2[i] == '*') {
					brojZvi++;
					indexZvi = i;
				}
			}
			if(brojZvi > 1) {
				throw new Exception("Previše zvijezdica!");
			}
			if(poljeZnakova2[0] == '*') {//na poèetku *
				for(int i = poljeZnakova1.length - 1, j = poljeZnakova2.length - 1; poljeZnakova2[j] != '*'; i--, j--) {
					if(poljeZnakova1[i] != poljeZnakova2[j]) {
						return false;
					}
				}
				return true;
			}else if(poljeZnakova2[poljeZnakova2.length - 1] == '*') {//na kraju *
				for(int i = 0, j = 0; poljeZnakova2[j] != '*'; i++, j++) {
					if(poljeZnakova1[i] != poljeZnakova2[j]) {
						return false;
					}
				}
				return true;
			}else{//ako je u sredini *
				if(!niz.substring(0, indexZvi).equals(uzorak.substring(0, indexZvi))) {
					return false;
				}
				if(!niz.substring(niz.length() - uzorak.substring(indexZvi + 1).length()).equals(uzorak.substring(indexZvi + 1))) {
					return false;
				}
				return true;
			}
		}
	};
	
	@Override
	public boolean satisfied(String value1, String value2) {
		return false;
	}
}