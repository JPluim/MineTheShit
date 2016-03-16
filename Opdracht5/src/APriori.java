import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;


public class APriori {
	/**
	 * The dataset. Each basket could be thought of as sentences, each entry as words.
	 */
	protected List<Set<String>> baskets;

	/**
	 * The threshold for when an item is frequent.
	 */
	protected int supportThreshold;

	/**
	 * Constructor for the A-Priori class.
	 * @param st The support threshold value.
	 */
	public APriori(int st) {
		supportThreshold = st;
		baskets = new ArrayList<Set<String>>();
	}

	/**
	 * Adds a basket (sentence) to the list of baskets.
	 * @param basket The basket to add.
	 */
	public void addBasket(String basket) {
		baskets.add(new HashSet<String>(Arrays.asList(basket.toLowerCase().split(" "))));
	}

	/**
	 * Computes all subsets of size k from set.
	 * @param set The set of which the subsets should be computed.
	 * @param k The size of the computed subsets.
	 * @return A set of subsets.
	 */
	public static Set<StringSet> getSubsets(Set<String> set, int k) {
		Set<StringSet> result = new HashSet<StringSet>();
		StringSet setList = new StringSet(set);

		StringSet subset = new StringSet();
		getSubsets_(setList, subset, k, result);
		return result;
	}

	/**
	 * Recursive method for getSubsets.
	 */
	private static void getSubsets_(StringSet set, StringSet subset, int subsetSize, Set<StringSet> candidates) {
		if (subsetSize == subset.size()) {
			candidates.add((StringSet)subset.clone());
		} else {
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				String s = it.next();
				subset.add(s);
				StringSet clone = new StringSet(set);
				clone.remove(s);
				getSubsets_(clone, subset, subsetSize, candidates);
				subset.remove(s);
			}
		}
	}

	/**
	 * Constructs candidates based on the previous set of frequent itemsets (L_k-1)
	 * @param filteredCandidates The set of frequent k-1 itemsets
	 * @return A set of candidate itemsets of size k
	 */
	public Set<StringSet> constructCandidates(Set<StringSet> filteredCandidates, int k) {
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

			for (int i = 0; i < temp.size(); i++) {
				HashSet<String> candidate = temp.get(i);

				for (int j = i+1; j < temp.size(); j++) {
					HashSet<String> other = temp.get(j);

					if(!candidate.equals(other)) {
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
			
		}

		return candidates;
	}

	/**
	 * Calculates the support value of each set in candidates.
	 * @param candidates The set of candidate sets.
	 * @param k The size of the candidates.
	 * @return A mapping of sets to support value.
	 */
	public Map<StringSet, Integer> countCandidates(Set<StringSet> candidates, int k) {
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
					}
				}
			}
		}

		return candidatesCount;
	}


	/**
	 * Removes those candidates that have a support value lower than supportThreshold.
	 * @param candidatesCount The map of sets to support value.
	 * @return A set of filtered candidates.
	 */
	public Set<StringSet> filterCandidates(Map<StringSet, Integer> candidatesCount) {
		// the result
		Set<StringSet> filteredCandidates = new HashSet<StringSet>();

		for(Entry<StringSet, Integer> e : candidatesCount.entrySet()) {
			if(e.getValue() >= this.supportThreshold) {
				filteredCandidates.add(e.getKey());
			}
		}

		return filteredCandidates;
	}

	/**
	 * Calculates frequent sets of size k from the baskets.
	 * @param k The size of the frequent sets.
	 * @return Set of frequent itemsets with size k
	 */
	public Set<StringSet> getFrequentSets(int k) {
		// the result
		Set<StringSet> filteredCandidates = null;

		for(int i = 1; i <= k; i++) {
			Set<StringSet> candidatesInput = constructCandidates(filteredCandidates, i);
			Map<StringSet, Integer> candidatesCounted = countCandidates(candidatesInput, i);

			filteredCandidates = filterCandidates(candidatesCounted);
		}

		return filteredCandidates;
	}


}
