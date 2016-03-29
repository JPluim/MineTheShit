import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class PCY extends APriori {

	private int bucketSize;

	private List<Integer> buckets = null;

	/**
	 * Constructor of the PCY algorithm class.
	 * @param s The support threshold.
	 * @param bs The bucket size.
	 */
	public PCY(int s, int bs) {
		super(s);

		bucketSize = bs;
	}

	/**
	 * Constructs candidates based on the previous set of frequent itemsets (L_k-1)
	 * @param filteredCandidates The set of frequent k-1 itemsets
	 * @return A set of candidate itemsets of size k
	 */
	@Override
	public Set<StringSet> constructCandidates(Set<StringSet> filteredCandidates, int k) {
		// PCY only acts on the frequent pairs
		if (k != 2) {
			return super.constructCandidates(filteredCandidates, k);
		}
		
		// the result
		Set<StringSet> candidates = new HashSet<StringSet>();

		if (filteredCandidates == null) {
			// add all initial words to the items set
			for (Set<String> basket: baskets) {
				for (String s: basket) {
					StringSet sl = new StringSet();
					sl.add(s);
					candidates.add(sl);
				}
			}
		} else {
			ArrayList<HashSet<String>> temp = new ArrayList<>();
			for (StringSet filteredCandidate : filteredCandidates) {
				HashSet<String> filteredHS = new HashSet<>();

				for (String s : filteredCandidate) {
					filteredHS.add(s);
				}

				temp.add(filteredCandidate);
			}

			int count = 0;

			for (int i = 0; i < temp.size(); i++) {
				HashSet<String> candidate = temp.get(i);

				for (int j = i+1; j < temp.size(); j++) {
					HashSet<String> other = temp.get(j);

					StringSet s1 = new StringSet(candidate);
					StringSet s2 = new StringSet(other);

					s1.addAll(s2);

					Integer hashKey = s1.hashCode() % bucketSize;
					if (hashKey < 0) {
						hashKey += bucketSize;
					}

					if(!candidate.equals(other) && buckets.get(hashKey) != null && buckets.get(hashKey) > this.supportThreshold) {
						count++;

						HashSet<String> candidateCopy1 = new HashSet<>(candidate);

						candidateCopy1.retainAll(other);
						if(candidateCopy1.size() == k-2) {
							HashSet<String> candidateCopy2 = new HashSet<>(candidate);
							candidateCopy2.addAll(other);

							StringSet sl = new StringSet();
							for (String s: candidateCopy2) {
								sl.add(s);
							}
							candidates.add(sl);
							//System.out.println(sl);

						}

					}

				}
			}

			System.out.println("count: " + count);

		}

		return candidates;
	}

	/**
	 * Calculates the support value of each set in candidates.
	 * @param candidates The set of candidate sets.
	 * @param k The size of the candidates.
	 * @return A mapping of sets to support value.
	 */
	@Override
	public Map<StringSet, Integer> countCandidates(Set<StringSet> candidates, int k) {
		// PCY only acts on the frequent pairs
		if (k != 1) {
			return super.countCandidates(candidates, k);
		}

		// initialize the buckets
		buckets = new ArrayList<Integer>(bucketSize);
		for (int i = 0; i < bucketSize; i++)
			buckets.add(0);

		// the result
		Map<StringSet, Integer> candidatesCount = new HashMap<StringSet, Integer>();

		for( Set<String> basket : baskets) {
			Set<StringSet> subsets = getSubsets(basket, k);

			for(StringSet subset : subsets) {
				for(StringSet candidate : candidates) {
					if(subset.equals(candidate)) {
						Integer count = candidatesCount.get(candidate);
						if(count == null) {
							candidatesCount.put(candidate, 1);
						} else {
							candidatesCount.put(candidate, count + 1);
						}
					} else if(subsets.contains(candidate)) {
						StringSet s1 = new StringSet(candidate);
						StringSet s2 = new StringSet(subset);

						s1.addAll(s2);
						Integer hashKey = s1.hashCode() % bucketSize;
						if (hashKey < 0) {
							hashKey += bucketSize;
						}

						if (buckets.get(hashKey) == null) {
							buckets.set(hashKey, 1);
						} else {
							buckets.set(hashKey, buckets.get(hashKey) + 1);
						}
					}
				}
			}
		}

		return candidatesCount;
	}

}
