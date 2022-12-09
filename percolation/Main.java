package percolation;
import edu.princeton.cs.algs4.StdRandom;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Percolation percolation = new Percolation(5);
		percolation.open(1, 1);
		percolation.open(1, 3);
		percolation.open(2, 5);
		percolation.open(3, 1);
		percolation.open(3, 3);
		percolation.open(1, 5);
		percolation.open(5, 5);
		System.out.println(percolation.isFull(1, 1));
		System.out.println(percolation.percolates());
		percolation.open(4, 3);
		percolation.open(2, 3);
		percolation.open(4, 4);
		percolation.open(5, 4);
		System.out.println(percolation.percolates());
		System.out.println("hello");
	}

}
