import java.util.List;
import java.util.Set;


public class Main {
	
	public static void exercise1_1() {
		ShingleSet shingles = new ShingleSet(2);
		shingles.shingleStrippedString("The quarterback scored a touchdown");
		//shingles.shingleString("The quarterback scored a touchdown");
		for (String s : shingles) {
			System.out.println(s);
		}
	}
	
	public static void exercise1_2() {
		// ADD CODE HERE
		
		//exercise1_3(mh);
	}
	
	public static void exercise1_3(MinHash mh) {
		// ADD CODE HERE
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// exercise 1.1
		exercise1_1();
		
		// exercise 1.2
		exercise1_2();
	}

}
