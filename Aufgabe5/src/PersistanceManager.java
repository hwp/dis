import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class PersistanceManager {
	private static final String LOG_PATH = "data/log";
	private static final String PAGE_PATH = "data/page";
	private static final int BUFFER_SIZE = 5;

	private static final PersistanceManager pm;

	private int tid;
	private int lsn;

	private PrintStream logout;

	private Map<Integer, String> buffer;
	private Set<Integer> commited;
	private Map<Integer, Collection<Integer>> pot; // Pages of Transaction

	static {
		try {
			pm = new PersistanceManager();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private PersistanceManager() throws IOException {
		tid = 0; // Might be updated in doRecovery()
		lsn = 0; // Might be updated in doRecovery()
		logout = new PrintStream(new FileOutputStream((new File(LOG_PATH)),
				true));
		buffer = new HashMap<Integer, String>();
		commited = new TreeSet<Integer>();
		pot = new HashMap<Integer, Collection<Integer>>();

		doRecovery();
	}

	private void doRecovery() throws IOException {
		File logfile = new File(LOG_PATH);
		if (logfile.exists()) {
			// Analysis
			Set<Integer> winner = new TreeSet<Integer>();
			BufferedReader in = new BufferedReader(new FileReader(logfile));
			String line;
			while ((line = in.readLine()) != null) {
				String[] tokens = line.split(",");
				if (tokens[2].equals("COMMIT")) {
					winner.add(Integer.parseInt(tokens[1]));
				} else if (tokens[2].equals("BOT")) {
					int t = Integer.parseInt(tokens[1]);
					if (t > tid) {
						tid = t;
					}
				}
				lsn = Integer.parseInt(tokens[0]);
			}
			in.close();
			tid++;
			lsn++;

			// Redo
			in = new BufferedReader(new FileReader(logfile));
			while ((line = in.readLine()) != null) {
				String[] tokens = line.split(",");
				if (tokens[2].equals("DATA")
						&& winner.contains(Integer.parseInt(tokens[1]))) {
					redo(Integer.parseInt(tokens[0]),
							Integer.parseInt(tokens[3]), tokens[4]);
				}
			}
			in.close();
		}
	}

	private void redo(int lsn, int pid, String data) throws IOException {
		File page = new File(PAGE_PATH + pid);
		boolean redo = true;
		if (page.exists()) {
			BufferedReader in = new BufferedReader(new FileReader(page));
			String line;
			if ((line = in.readLine()) != null) {
				String[] tokens = line.split(",");
				if (Integer.parseInt(tokens[0]) >= lsn) {
					redo = false;
				}
			}
			in.close();
		}

		if (redo) {
			System.err.println("REDO : LSN=" + lsn + " PAGE=" + pid);
			PrintStream out = new PrintStream(page);
			out.println(lsn + "," + data);
			out.close();
		}
	}

	private synchronized int log(String content) {
		logout.println(lsn + "," + content);
		return lsn++;
	}

	static public PersistanceManager getInstance() {
		return pm;
	}

	public int beginTransaction() {
		int r;
		synchronized (this) {
			r = tid;
			tid++;
		}
		log(r + "," + "BOT");
		pot.put(r, new TreeSet<Integer>());

		return r;
	}

	public void write(int taid, int pageid, String data) {
		int n = log(taid + "," + "DATA," + pageid + "," + data);
		buffer.put(pageid, n + "," + data);
		pot.get(taid).add(pageid);

		// Check full
		synchronized (commited) {
			if (commited.size() >= BUFFER_SIZE) {
				for (int pid : commited) {
					persistData(pid);
				}
				commited.clear();
			}
		}
	}

	private void persistData(int pid) {
		try {
			PrintStream out = new PrintStream(new File(PAGE_PATH + pid));
			out.println(buffer.get(pid));
			out.close();
			buffer.remove(pid);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void commit(int taid) {
		log(taid + "," + "COMMIT");
		synchronized (pot) {
			for (int pid : pot.get(taid)) {
				commited.add(pid);
			}
			pot.remove(taid);
		}
	}
}
