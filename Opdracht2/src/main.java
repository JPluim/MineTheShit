import java.util.ArrayList;

public class main {

	public static void perceptron() {

		Dataset d = new Dataset("data/gaussian.txt", true);

		Perceptron p = new Perceptron(1);
		p.updateWeights(d);

		PerceptronPlotter pp = new PerceptronPlotter("Set1", "Set2");
		pp.plotData(d, p);

	}

	public static void perceptronDigits() {

		Dataset d_training = new Dataset("data/train_digits.txt", true);

		//ArrayList<Double> l = d.get(0);
		//System.out.println(l.toString());

		//DigitFrame df = new DigitFrame("Test digitframe", l, 8, 8);

		Perceptron p_train = new Perceptron(1);
		p_train.updateWeights(d_training);
		p_train.updateWeights(d_training);

		DigitFrame df = new DigitFrame("Training Data", p_train.getWeights(), 8, 8);

		Integer misclassified = 0;

		Dataset d_test = new Dataset("data/test_digits.txt", true);
		for(FeatureVector fv : d_test) {
			if(p_train.predict(fv) != fv.getLabel()) {
				misclassified++;
			}
		}

		System.out.println("Total misclassified: " + misclassified + "/" + d_test.size());


	}

	public static void nearestNeighbour() {
		// add code here
	}
	
	public static void nearestNeighbourDigits() {
		// add code here
	}

	public static void main(String[] args) {
		//perceptron();
		perceptronDigits();
		//nearestNeighbour();
		//nearestNeighbourDigits();
	}

}
