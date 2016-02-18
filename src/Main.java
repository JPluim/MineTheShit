import java.util.List;
import java.util.Set;


public class Main {
	
	public static void exercise1_1() {
		ShingleSet shingles1 = new ShingleSet(5);
		ShingleSet shingles2 = new ShingleSet(5);
		shingles1.shingleString("The plane was ready for touch down");
		shingles2.shingleString("The quarterback scored a touchdown");
		//shingles.shingleString("The quarterback scored a touchdown");
		System.out.println(shingles1.jaccardDistance(shingles2));
		
		ShingleSet shingles3 = new ShingleSet(5);
		ShingleSet shingles4 = new ShingleSet(5);
		shingles3.shingleStrippedString("The plane was ready for touch down");
		shingles4.shingleStrippedString("The quarterback scored a touchdown");
		//shingles.shingleString("The quarterback scored a touchdown");
		System.out.println(shingles3.jaccardDistance(shingles4));
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
