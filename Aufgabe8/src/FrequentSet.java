import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class FrequentSet {
	private static Map<Integer, List<Integer>> index;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Read data
		List<Set<Integer>> transactions = new ArrayList<Set<Integer>>();
		index = new TreeMap<Integer, List<Integer>>();
		int noft = 0;

		try {
			BufferedReader in = new BufferedReader(new FileReader(args[0]));
			String line;
			while ((line = in.readLine()) != null) {
				Set<Integer> t = new TreeSet<Integer>();
				for (String token : line.split("\\s")) {
					int a = Integer.parseInt(token);
					t.add(a);
					if (index.containsKey(a)) {
						index.get(a).add(noft);
					} else {
						List<Integer> l = new ArrayList<Integer>();
						l.add(noft);
						index.put(a, l);
					}
				}
				transactions.add(t);
				noft++;
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		final int MIN_SUP = (int) (.01 * noft);
		System.out.println("MIN_SUP = " + MIN_SUP);

		// Initiate candidate set
		List<List<Integer>> cand = new ArrayList<List<Integer>>();
		for (Entry<Integer, List<Integer>> e : index.entrySet()) {
			if (e.getValue().size() >= MIN_SUP) {
				cand.add(Collections.singletonList(e.getKey()));
			}
		}

		int k = 1;
		while (!cand.isEmpty()) {
			System.out.println("#Frequent Item Set of " + k + " item(s)");
			for (List<Integer> s : cand) {
				System.out.println(s);
			}

			List<List<Integer>> prev = cand;
			cand = new ArrayList<List<Integer>>();

			ListIterator<List<Integer>> iter = prev.listIterator();
			while (iter.hasNext()) {
				List<Integer> s1 = iter.next();
				ListIterator<List<Integer>> iter2 = prev.listIterator(iter
						.nextIndex());
				while (iter2.hasNext()) {
					List<Integer> s2 = iter2.next();
					boolean e = true;
					for (int i = 0; i < k - 1; i++) {
						if (s1.get(i) != s2.get(i)) {
							e = false;
							break;
						}
					}
					if (e && s1.get(k - 1) < s2.get(k - 1)) {
						List<Integer> n = new ArrayList<Integer>(k + 1);
						n.addAll(s1);
						n.add(s2.get(k - 1));

						// All subset is in cand
						// TODO

						// Count
						// System.out.println(n + " : " + count(n));
						if (count(n) >= MIN_SUP) {
							cand.add(n);
						}
					}
				}
			}
			k++;
		}
	}

	private static int count(List<Integer> itemset) {
		if (itemset.size() == 1) {
			return index.get(itemset.get(0)).size();
		} else {
			int c = 0;

			// List<Set<Integer>> lat = new ArrayList<Set<Integer>>(
			// itemset.size() - 1);
			// for (int i = 1; i < itemset.size(); i++) {
			// lat.add(index.get(itemset.get(i)));
			// }
			//
			// for (int t : index.get(itemset.get(0))) {
			// c++;
			// for (Set<Integer> s : lat) {
			// if (!s.contains(t)) {
			// c--;
			// break;
			// }
			// }
			// }

			List<List<Integer>> ll = new ArrayList<List<Integer>>(
					itemset.size());
			int[] p = new int[itemset.size()];
			for (int i = 0; i < itemset.size(); i++) {
				ll.add(index.get(itemset.get(i)));
				p[i] = 0;
			}

			while (true) {
				for (int i = 0; i < itemset.size() - 1; i++) {
					while (!ll.get(i).get(p[i])
							.equals(ll.get(i + 1).get(p[i + 1]))) {
						if (ll.get(i).get(p[i]) < ll.get(i + 1).get(p[i + 1])) {
							p[i]++;
							if (p[i] == ll.get(i).size()) {
								return c;
							}
							i = 0;
						} else {
							p[i + 1]++;
							if (p[i + 1] == ll.get(i + 1).size()) {
								return c;
							}
						}
					}
				}
				c++;
				p[0]++;
				if (p[0] == ll.get(0).size()) {
					return c;
				}
			}
		}
	}
}
