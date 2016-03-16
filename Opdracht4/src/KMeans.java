import java.util.*;

public class KMeans {
	/**
	 * The number of clusters to detect.
	 */
	private int k;
	
	/**
	 * A collection of (k) clusters.
	 */
	private List<Cluster> clusters;
	
	/**
	 * The unclustered data.
	 */
	private Cluster data;

	/**
	 * Constructor.
	 * @param k The number of clusters to detect.
	 * @param fileName The filename which holds the cluster data.
	 */
	public KMeans(int k, String fileName) {
		this.k = k;
		clusters = new ArrayList<Cluster>();
		data = new Cluster();

		readData(fileName);
	}

	/**
	 * @param i The index of the cluster that is to be retrieved.
	 * @return The cluster at index i.
	 */
	public Cluster getCluster(int i) {
		return clusters.get(i);
	}
	
	/**
	 * @return The unclustered data.
	 */
	public Cluster getData() {
		return data;
	}
	
	/**
	 * @return The collection of clusters.
	 */
	public List<Cluster> getClusters() {
		return clusters;
	}
	
	/**
	 * @return The number of clusters.
	 */
	public int getClusterSize() {
		return clusters.size();
	}

	/**
	 * Reads in the data of filename.
	 * @param fileName The file which is to be read.
	 */
	private void readData(String fileName) {
		data.readData(fileName);
	}

	/**
	 * Adds a new init point at a random location in the dataset.
	 */
	private void addInitPoints() {

		Random randomGenerator = new Random();
		ArrayList<FeatureVector> randomPoints = new ArrayList<>();

		for(int i = 0; i < k; i++) {
			int randomInt = randomGenerator.nextInt(data.size());

			Cluster c = new Cluster();
			c.add(data.get(randomInt));

			clusters.add(c);
			randomPoints.add(data.get(randomInt));
			data.remove(randomInt);
		}

		for(int i = 0; i < data.size(); i++) {
			Cluster point = new Cluster();
			FeatureVector fv = data.get(i);
			point.add(fv);

			Cluster closestCluster = new Cluster();
			double minDist = Double.MAX_VALUE;

			for(int j = 0; j < clusters.size(); j++) {
				if(point.minDistanceTo(clusters.get(j)) < minDist) {
					minDist = point.minDistanceTo(clusters.get(j));
					closestCluster = clusters.get(j);
				}
			}

			closestCluster.add(data.get(i));
		}

		data.addAll(randomPoints);

	}
	
	/**
	 * Clears all elements from the clusters.
	 */
	private void clearClusters() {
		for (Cluster c: clusters) {
			c.clear();
		}
	}

	/**
	 * Computes clusters based on the centroids of the clusters in the previous round.
	 * If no such clusters exist yet, it will select k random points.
	 */
	public void update() {
		if(clusters.size() == 0) {
			addInitPoints();
		}

		ArrayList<FeatureVector> centroids = new ArrayList<>();

		for(int i = 0; i < clusters.size(); i++) {
			Cluster c = clusters.get(i);
			centroids.add(c.centroid());
		}

		clearClusters();

		HashMap<Integer, Cluster> clustersTemp = new HashMap<>();

		for(int i = 0; i < data.size(); i++) {
			FeatureVector point = data.get(i);
			//System.out.println(point.toString());

			Double minDistance = Double.MAX_VALUE;
			int minIndex = -1 ;
			for(int j = 0; j < centroids.size(); j++) {
				FeatureVector centroid = centroids.get(j);
				if(centroid.distance(point) < minDistance) {
					minDistance = centroid.distance(point);
					minIndex = j;
				}
			}

			//System.out.println(minIndex + " - " + minDistance);

			Cluster c = clustersTemp.get(minIndex);
			if(c == null) {
				c = new Cluster();
			}
			c.add(point);
			clustersTemp.put(minIndex, c);

			//System.out.println(c.toString());
			//System.out.println();

		}

		for(int i = 0; i < clusters.size(); i++) {
			clusters.set(i, clustersTemp.get(i));
		}
	}

	public void update(int count) {

		for(int i = 0; i < count; i++) {
			update();
		}

	}
}
