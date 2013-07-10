import java.util.Comparator;
import java.util.List;

public class ListComparator implements Comparator<List<Integer>> {
	@Override
	public int compare(List<Integer> arg0, List<Integer> arg1) {
		int s = arg0.size() - arg1.size();
		if (s != 0) {
			return s;
		} else {
			for (int i = 0; i < arg0.size(); i++) {
				int d = arg0.get(i) - arg1.get(i);
				if (d != 0) {
					return d;
				}
			}
		}

		return 0;
	}
}