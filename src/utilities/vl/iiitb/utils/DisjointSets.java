
package vl.iiitb.utils;

import java.util.HashMap;

/**
 * Disjoint set class, using union by rank and path compression. 
 * Adapted using the code by Mark Allen Weiss
 */
public class DisjointSets {

	HashMap<Integer, Integer> set;

	public DisjointSets() {
		set = new HashMap<Integer, Integer>();
	}

	public void clear() {
		set.clear();
	}
	/**
	 * Union two disjoint sets using the height heuristic. root1 and root2 are
	 * distinct and represent set names.
	 * 
	 * @param root1
	 *            the root of set 1.
	 * @param root2
	 *            the root of set 2.
	 */
	public void union(int root1, int root2) {
		if (root1 == root2)
			return;

		int r1 = set.get(root1);
		int r2 = set.get(root2);

		if (r2 < r1) {
			// root2 is deeper
			// Make root2 new root
			set.remove(root1);
			set.put(root1, root2);
		} else {
			if (r1 == r2) {
				// Update height if same
				r1--;
				set.remove(root1);
				set.put(root1, r1);
			}
			// Make root1 new root
			set.remove(root2);
			set.put(root2, root1);
		}
	}

	/**
	 * Perform a find with path compression.
	 * 
	 * @param x
	 *            the element being searched for.
	 * @return the set containing x.
	 */
	public int find(int x) {
		Integer f = set.get(x);
		if (f == null) {
			f = -1;
			set.put(x, -1);
		}
		if (f < 0) {
			return x;
		} else {
			int xx = find(f);
			set.remove(x);
			set.put(x, xx);
			return xx;
		}
	}


	// Test main; all finds on same output line should be identical
	public static void main(String[] args) {
		int numElements = 128;
		int numInSameSet = 16;

		// DisjointSets ds = new DisjointSets( numElements );
		DisjointSets ds = new DisjointSets();
		int set1, set2;

		for (int k = 1; k < numInSameSet; k *= 2) {
			for (int j = 0; j + k < numElements; j += 2 * k) {
				set1 = ds.find(j);
				set2 = ds.find(j + k);
				ds.union(set1, set2);
			}
		}

		for (int i = 0; i < numElements; i++) {
			System.out.print(ds.find(i) + "*");
			if (i % numInSameSet == numInSameSet - 1)
				System.out.println();
		}
		System.out.println();
	}
}