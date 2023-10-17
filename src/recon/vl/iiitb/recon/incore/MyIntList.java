
package vl.iiitb.recon.incore;

import java.util.Arrays;

public class MyIntList {
	public int [] array;
	public int length = 0;
	
	public MyIntList(int no) {
		array = new int[no];
	}
	
	public MyIntList() {
		array = new int[10];
	}
	
	public void add(int n) {
		if(array == null) {
			array = new int[10];
		}
		if(length == array.length) {
			array = Arrays.copyOf(array, (int) (length * 1.5));
		}
		array[length ++] = n;
	}
	
	public int size() {
		return length;
	}
	
	public int get(int i) {
		return array[i];
	}

	public void clear() {
		array = null;
		length = 0;
	}

	public void set(int pos, int val) {
		array[pos] = val;		
	}
}
