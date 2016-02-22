import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class Main {
	
	public static void exercise1_1() {
		ShingleSet shingles1 = new ShingleSet(5);
		ShingleSet shingles2 = new ShingleSet(5);
		shingles1.shingleString("The plane was ready for touch down");
		shingles2.shingleString("The quarterback scored a touchdown");

		System.out.println("Jaccard distance shingles: ");
		System.out.println(shingles1.jaccardDistance(shingles2) + "\n");
		
		ShingleSet shingles3 = new ShingleSet(5);
		ShingleSet shingles4 = new ShingleSet(5);
		shingles3.shingleStrippedString("The plane was ready for touch down");
		shingles4.shingleStrippedString("The quarterback scored a touchdown");

		System.out.println("Jaccard distance shingles (excluding spaces): ");
		System.out.println(shingles3.jaccardDistance(shingles4));
	}
	
	public static void exercise1_2() {

		Integer k = 1;

		ShingleSet s1 = new ShingleSet(k);
		s1.shingleStrippedString("ad");

		ShingleSet s2 = new ShingleSet(k);
		s2.shingleStrippedString("c");

		ShingleSet s3 = new ShingleSet(k);
		s3.shingleStrippedString("bde");

		ShingleSet s4 = new ShingleSet(k);
		s4.shingleStrippedString("acd");

		MinHash minHash = new MinHash();

		HashFunction hf1 = new HashFunction(1,1);
		HashFunction hf2 = new HashFunction(3,1);

		minHash.addSet(s1);
		minHash.addSet(s2);
		minHash.addSet(s3);
		minHash.addSet(s4);

		minHash.addHashFunction(hf1);
		minHash.addHashFunction(hf2);

		MinHashSignature result = minHash.computeSignature();
		System.out.println(result.toString());

		//exercise1_3(mh);
	}
	
	public static void exercise1_3() {

		Integer k = 1;

		ShingleSet s1 = new ShingleSet(k);
		s1.shingleStrippedString("ad");

		ShingleSet s2 = new ShingleSet(k);
		s2.shingleStrippedString("c");

		ShingleSet s3 = new ShingleSet(k);
		s3.shingleStrippedString("bde");

		ShingleSet s4 = new ShingleSet(k);
		s4.shingleStrippedString("acd");

		MinHash minHash = new MinHash();

		minHash.addSet(s1);
		minHash.addSet(s2);
		minHash.addSet(s3);
		minHash.addSet(s4);

		minHash.addRandomHashFunctions(100);

		MinHashSignature result = minHash.computeSignature();

		TreeMap<Integer, List<Integer>> candidates = LSH.computeCandidates(result, 1000, 5);

		for(Map.Entry<Integer,List<Integer>> entry : candidates.entrySet()) {
			Integer key = entry.getKey();
			List<Integer> value = entry.getValue();

			for (Integer v : value) {
				System.out.println(key + " => " + v);
			}
		}


	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// exercise 1.1
		//exercise1_1();
		
		// exercise 1.2
		//exercise1_2();

		//exercise1_3();

		Integer k = 1;

		ShingleSet s1 = new ShingleSet(k);
		s1.shingleStrippedString("ad");

		ShingleSet s2 = new ShingleSet(k);
		s2.shingleStrippedString("c");

		ShingleSet s3 = new ShingleSet(k);
		s3.shingleStrippedString("bde");

		ShingleSet s4 = new ShingleSet(k);
		s4.shingleStrippedString("acd");

		System.out.println(s3.jaccardDistance(s4));
	}

}
