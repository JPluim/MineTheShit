import java.util.ArrayList;

public class main {

	private static void hierarchical() {
		// add code here
		
		//HierarchicalClusteringPlotter plotter = new HierarchicalClusteringPlotter(3, "data\\cluster.txt");
		HierarchicalClusteringPlotter plotter = new HierarchicalClusteringPlotter(3, "data/cluster.txt");
		//HierarchicalClusteringPlotter plotter = new HierarchicalClusteringPlotter(3, "data\\cluster_lines.txt");
		//HierarchicalClusteringPlotter plotter = new HierarchicalClusteringPlotter(3, "data/cluster_lines.txt");
	}

	private static void hierarchicalDigits() {
		HierarchicalClustering HC = new HierarchicalClustering(10, "data/train_digits.txt");

		while (HC.getClusters().size() > 10) {
			HC.update();
		}

		for(int i = 0; i < HC.getClusters().size(); i++) {
			new DigitFrame("Hierarchical Clustering digitframe", HC.getCluster(i).centroid(), 8, 8);
		}

	}

	private static void kmeans() {
		KMeansPlotter test = new KMeansPlotter(3, "data/cluster.txt");
	}

	private static void kmeansTuneK() {
		for(int k = 1; k <= 10; k++) {
			KMeans KM = new KMeans(k, "data/cluster.txt");
			KM.update(10);

			double averageRSS = 0;
			for(int j = 0; j < KM.getClusters().size(); j++) {
				averageRSS = (averageRSS * j + KM.getCluster(j).calculateAverageRSS()) / (j+1);
			}

			System.out.println("{k: " + k + ", RSS: " + averageRSS);
		}
	}

	private static void kmeansDigits() {

		KMeans KM = new KMeans(10, "data/train_digits.txt");

		KM.update(10);

		for(int i = 0; i < KM.getClusters().size(); i++) {
			new DigitFrame("K-means digitframe", KM.getCluster(i).centroid(), 8, 8);
		}

	}

	public static void main(String[] args) {
		hierarchical();
		//hierarchicalDigits();
		//kmeans();
		//kmeansTuneK();
		//kmeansDigits();
	}

}
