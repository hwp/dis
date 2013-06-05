public class PersistanceManager {
	static final private PersistanceManager pr;
	
	static {
		try {
			pr = new PersistanceManager();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private PersistanceManager() {
		// empty
	}

	static public PersistanceManager getInstance() {
		return pr;
	}

	public int beginTransaction() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void write(int taid, int pageid, String data) {
		// TODO Auto-generated method stub
		
	}

	public void commit(int taid) {
		// TODO Auto-generated method stub
		
	}
	
	
}
