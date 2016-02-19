import javafx.util.Pair;

import java.util.*;

/**
 * 
 * @author hansgaiser
 *
 */
public class LSH {
	
	/**
	 * Computes the candidate pairs using the LSH technique.
	 * @param mhs The minhash signature object.
	 * @param bs The number of buckets to be used in the LSH table.
	 * @param r The number of rows per band to be used.
	 * @return Returns a set of indices pairs that are candidate to being similar.
	 */
	public static TreeMap<Integer, List<Integer>> computeCandidates(MinHashSignature mhs, int bs, int r) {
		// assert that the number of rows can be evenly divided by r
		assert(mhs.rows() % r == 0);
		
		// the number of bands
		int b = mhs.rows() / r;
		
		// the result
		//Set<Pair<Integer,Integer>> candidates = new HashSet<Pair<Integer,Integer>>();
		TreeMap<Integer, List<Integer>> candidates = new TreeMap<>();

		//iterate over the bands
		for(Integer i = 0; i < b; i++) {

			TreeMap<Integer,List<Integer>> buckets = new TreeMap<>();

			for(Integer j = 0; j < mhs.cols(); j++) {

				String s = mhs.colSegment(j, i*r, (i*r) + r);
				Integer s_hash = s.hashCode() % bs;

				List<Integer> l = buckets.get(s_hash);

				if(l == null) {
					l = new LinkedList<>();
				}

				l.add(j);
				buckets.put(s_hash, l);

			}

			for(Integer key : buckets.keySet()) {
				List<Integer> cand = buckets.get(key);
				System.out.println(cand.toString());

				if(cand.size() > 1) {

					for (Integer cand1 : cand) {
						for (Integer cand2 : cand) {
							System.out.println(candidates.toString());
							System.out.println(cand1 + " " + cand2);


							if (!cand1.equals(cand2)) {
								//Pair p = new Pair(cand1, cand2);
								//candidates.add(p);

								List<Integer> l = candidates.get(cand1);

								if(l == null) {
									l = new LinkedList<>();
								}
								l.add(cand2);

								candidates.put(cand1, l);

								//System.out.println(p.toString());
							}

						}
					}
				}

			}

		}
		
		return candidates;
	}
	
}
