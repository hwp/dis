public class Client extends Thread {
	private String name;
	private int offset;
	private int size;

	public Client(String name, int offset, int size) {
		super();
		this.name = name;
		this.offset = offset;
		this.size = size;
	}

	public void run() {
		PersistanceManager pm = PersistanceManager.getInstance();
		int tid = pm.beginTransaction();
		System.err.println("Tranaction " + tid + " begin");
		for (int i = 0; i < size; i++) {
			try {
				long interval = (long) (500 * (.5 + Math.random()));
				Thread.sleep(interval);
			} catch (InterruptedException e) {
			}
			pm.write(tid, offset + i, name + " entry " + i);
		}
		if (Math.random() < 0.8) {
			pm.commit(tid);
			System.err.println("Tranaction " + tid + " commit");
		}
		else{
			System.err.println("Tranaction " + tid + " aborted without commit");
		}
	}

}
