import java.util.SortedSet;
import java.util.TreeSet;


/**
 * The ShingleSet class contains a sorted set of shingles.
 * @author hansgaiser
 *
 */
public class ShingleSet extends TreeSet<String> implements SortedSet<String> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2143400328259342668L;
	
	/**
	 * The size of the shingles.
	 */
	private int k;
	
	/**
	 * Constructor for the ShingleSet.
	 * @param k The size of the shingles.
	 */
	public ShingleSet(int k) {
		this.k = k;
	}
	
	/**
	 * Add shingles of size k to the set from String s.
	 * @param s The string that is to be transformed to shingles.
	 */
	public void shingleString(String s) {
		int l = s.length();
		if (l > k) {
			int pointer = 0;
			while (pointer + k < l + 1) {
				this.add(s.substring(pointer, pointer + k));
				pointer++;
			}
		}
		else {
			this.add(s); //???????
		}
	}
	
	/**
	 * Add shingles of size k to the set from String s, where all white spaces from s are removed.
	 * @param s The string that is to be transformed to shingles.
	 */
	public void shingleStrippedString(String s) {
		String stringWithoutSpaces = s.replace(" ", "");
		shingleString(stringWithoutSpaces);
	}
	
	/**
	 * Calculates the Jaccard distance between this set and the other set.
	 * @param other The other set of shingles that this set will be compared to.
	 * @return The Jaccard distance between this set and the other set.
	 */
	public double jaccardDistance(TreeSet<String> other) {
		for (String string : this) {
			System.out.println(string);
		}
		System.out.println();
		for (String string : other) {
			System.out.println(string);
		}
		System.out.println();
		TreeSet<String> inter = (TreeSet<String>) this.clone();
		inter.retainAll(other);
		
		for (String string : inter) {
			System.out.println(string);
		}
		TreeSet<String> union = (TreeSet<String>) this.clone();
		union.addAll(other);
		System.out.println();
		for (String string : union) {
			System.out.println(string);
		}
		System.out.println(union.size());
		System.out.println(inter.size());
		return 1 - ( (double)inter.size() / union.size());		
	}
}