import java.util.HashMap;
import java.util.Map;


public class TaxationPageRank extends PageRank {
	/**
	 * The taxation value.
	 */
	double beta;

	/**
	 * Constructor.
	 * @param b The beta parameter for the taxation version of PageRank.
	 */
	public TaxationPageRank(double b) {
		super();

		beta = b;
	}

	/**
	 * Calculates the PageRank using the taxation method.
	 */
	@Override
	public Map<String, Double> calculatePageRank(int iterations) {
		// the result
		HashMap<String, Double> result = new HashMap<String, Double>();

		// the tools
		Matrix transitionMatrix = constructTransitionMatrix();
		Matrix randomSurfer = getRandomSurferVector();
		Matrix unitVector = randomSurfer;

		// FILL IN YOUR CODE HERE
		for (int i = 0; i < iterations; i++) {
			randomSurfer = transitionMatrix.dot(randomSurfer).multiply(beta).add(unitVector.multiply(1 - beta));
		}

		// fill the results, match names with PageRank values
		int count = 0;
		for (String s: data.keySet()) {
			result.put(s, randomSurfer.get(count));
			count++;
		}

		return result;
	}
}
