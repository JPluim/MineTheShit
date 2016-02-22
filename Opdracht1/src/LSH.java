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
		TreeMap<Integer, List<Integer>> candidates = new TreeMap<>();

		//iterate over the bands
		for(Integer i = 0; i < b; i++) {

			//create a bucket in which we can save all sets for a hash
			TreeMap<Integer,List<Integer>> buckets = new TreeMap<>();

			//iterate over all columns (representing sets)
			for(Integer j = 0; j < mhs.cols(); j++) {

				//get the column segment belong to column j of size r. Function colSegment is exclusive for the last element.
				String s = mhs.colSegment(j, i*r, (i+1)*r);

				//calculate the hash for the column segment
				Integer s_hash = s.hashCode() % bs;

				//add the set to the list of sets already assigned to the hash, or create a new list if no sets have been
				//assigned yet
				List<Integer> l = buckets.get(s_hash);
				if(l == null) {
					l = new LinkedList<>();
				}

				l.add(j);
				buckets.put(s_hash, l);

			}

			//iterate over all buckets and check if buckets contain more than 1 element
			for(Integer key : buckets.keySet()) {
				List<Integer> cand = buckets.get(key);

				//if more than 1 column segment is hashed to a bucket, save all combinations of elements in that bucket
				//as candidates.
				if(cand.size() > 1) {

					for (Integer j = 0; j < cand.size(); j++) {
					Integer cand1 = cand.get(j);

						for (Integer k = j+1; k < cand.size(); k++) {
							Integer cand2 = cand.get(k);

							if (!cand1.equals(cand2)) {

								List<Integer> l = candidates.get(cand1);

								if(l == null) {
									l = new LinkedList<>();
								}

								if(!l.contains(cand2)) {
									l.add(cand2);
									candidates.put(cand1, l);
								}
							}

						}
					}
				}

			}

		}
		
		return candidates;
	}
	
}
