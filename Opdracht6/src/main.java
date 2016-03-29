import java.util.ArrayList;
import java.util.List;


public class main {
	
	/**
	 * Computes nrVectors eigen vectors of m where e is the
	 * stopping criterion for the norm of the difference for an
	 * eigenvector in between two rounds.
	 * @param m The matrix of which eigenvectors should be computed.
	 * @param nrVectors The number of eigenvectors to compute.
	 * @param e The threshold for the stopping criterion.
	 * @return A list of eigenvectors in m.
	 */
	public static List<Matrix> powerIteration(Matrix m, int nrVectors, double e) {
		assert(m.cols() == m.rows() && m.cols() >= nrVectors);
		
		List<Matrix> eigenvectors = new ArrayList<Matrix>();
		
		// add code here
		for(int i = 0; i < nrVectors; i++) {

			System.out.println("Eigenvector " + (i+1));

			Matrix v = new Matrix(m.cols(),1,1);

			double norm = v.norm();
			while(norm > e) {

				Matrix prod = m.dot(v);
				Matrix v_new = prod.multiply(1/prod.norm());

				Matrix test_v = new Matrix(m.cols(), 1, v_new);
				test_v  = test_v.multiply(-1.0);

				norm = test_v.add(v).norm();
				v = v_new;
			}

			eigenvectors.add(v);

			double lambda = v.transpose().dot(m).dot(v).get(0,0);
			m = m.add((v.dot(v.transpose()).multiply(lambda)).multiply(-1));
		}

		//System.out.println(eigenvectors);

		return eigenvectors;
	}
	
	/**
	 * Computes two eigenvectors of a small matrix example.
	 */
	public static void powerIterationTest() {
		// add code here
		Matrix test = Matrix.readData("data/matrix.txt");

		powerIteration(test, 2, 10E-5);
	}

	/**
	 * Computes the principal components from a Gaussian
	 * distributed dataset.
	 */
	public static void pca() {
		// add code here
		Matrix data = Matrix.readData("data/gaussian.txt");

		PCAPlotter plotter = new PCAPlotter();
		plotter.plotData(data);

		Matrix mean_row = data.meanRow();
		Matrix diff = data.subtractRow(mean_row);
		diff = diff.transpose().dot(diff);

		double multiplication_factor = 1.0 / data.rows();
		Matrix cov_matrix = diff.multiply(multiplication_factor);

		List<Matrix> eigenVectors = powerIteration(cov_matrix, 2, 10E-5);

		plotter.plotEigenvectors(eigenVectors);
	}
	
	/**
	 * Computes some principal components from a dataset
	 * of face images.
	 */
	public static void pcaFaces() {
		// add code here
		Matrix data = Matrix.readData("data/faces.txt");

		Matrix mean_row = data.meanRow();
		Matrix diff = data.subtractRow(mean_row);
		diff = diff.transpose().dot(diff);

		double multiplication_factor = 1.0 / data.rows();
		Matrix cov_matrix = diff.multiply(multiplication_factor);

		List<Matrix> eigenVectors = powerIteration(cov_matrix, 10, 10E-5);

		for(Matrix m : eigenVectors) {
			ImageFrame IF = new ImageFrame("Faces");
			IF.showImage(m, 32, 32);
		}

	}

	public static void main(String[] args) {
		//powerIterationTest();
		//pca();
		//pcaFaces();
	}

}
