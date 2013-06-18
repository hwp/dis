public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Press ENTER to start");
		System.console().readLine();

		Client c1 = new Client("Max", 0, 4);
		Client c2 = new Client("Jerry", 10, 6);
		Client c3 = new Client("Fritz", 20, 5);
		Client c4 = new Client("Harry", 30, 4);
		Client c5 = new Client("XYZ", 40, 5);

		c1.start();
		c2.start();
		c3.start();
		c4.start();
		c5.start();
	}
}
