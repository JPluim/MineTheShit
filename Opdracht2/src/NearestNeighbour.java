import java.util.*;
import java.util.Map.Entry;


public class NearestNeighbour {
	/**
	 * List of training vectors.
	 */
	Dataset dataset;

	/**
	 * Constructor.
	 */
	public NearestNeighbour() {
		dataset = new Dataset();
	}

	/**
	 * Reads in the data from a text file.
	 * @param fileName
	 */
	public void readData(String fileName) {
		dataset.readData(fileName, false);
	}

	/**
	 * 
	 * @return
	 */
	public Dataset getDataset() {
		return dataset;
	}

	/**
	 * Classifies a query. Finds the k nearest neighbours and scales them if necessary.
	 * @param features The query features.
	 * @param k The number of neighbours to select.
	 * @return Returns the label assigned to the query.
	 */
	public int predict(List<Double> features, int k) {
		// the result
		int label = -1;

		FeatureVector fv = new FeatureVector(-1);
		fv.addAll(features);

		LinkedList<Measurement> neighbors = new LinkedList<>();

		for(FeatureVector fv_compare : dataset) {

			Measurement measure = new Measurement(fv_compare, fv.distance(fv_compare));

			neighbors.add(measure);

		}

		Collections.sort(neighbors);

		TreeMap<Integer, Integer> votes = new TreeMap<>();

		for(Integer j = 0; j < k; j++) {
			FeatureVector fv_votes = neighbors.get(j).getFeatureVector();
			Integer label_votes = votes.get(fv_votes.getLabel());

			if(label_votes != null) {
				label_votes = label_votes + 1;
				votes.put(fv_votes.getLabel(), label_votes);
			} else {
				votes.put(fv_votes.getLabel(), 1);
			}
		}

		Map.Entry<Integer, Integer> maxEntry = null;
		for (Map.Entry<Integer, Integer> entry : votes.entrySet())
		{
			if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
			{
				maxEntry = entry;
			}
		}

		label = maxEntry.getKey();

		return label;
	}
}
