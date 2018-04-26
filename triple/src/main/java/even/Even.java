package even;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Even {

	ArrayList<Blob> blobList = new ArrayList<Blob>();
	ArrayList<int[]> charList = new ArrayList<int[]>();
	int w = 0;
	int l = 0;

	public Even() {
	}

	public void loadLine(String row) {
		w = row.length();

		int[] array = new int[row.length()];
		charList.add(array);
		for (int i = 0; i < row.length(); i++) {
			int c = row.charAt(i);
			if (c == '*') {
				array[i] = 1;
			}
		}
		l = charList.size();
	}

	public void solve() {
		Blob b = Blob.create();
		for (int y = 0; y < this.l; y++) {
			for (int x = 0; x < this.w; x++) {
				int current = get(x, y);
				if (current == 1) {
					search(x, y, b);
					b = Blob.create();
				}
			}
		}
		for (Blob blob : Blob.blobMap.values()) {
			isValid(blob);
		}
	}

	public void search(int x, int y, Blob b) {
		if (x < 0 || y < 0 || x >= this.w || y >= this.l) {
			return;
		}
		final int i = get(x, y);
		if (i == 1) {
			set(x, y, b.id);
			b.update(x, y);

			search(x - 1, y, b);
			search(x + 1, y, b);
			search(x, y - 1, b);
			search(x, y + 1, b);

		} else {
			return;
		}

	}

	public int get(int x, int y) {
		return charList.get(y)[x];
	}

	public void set(int x, int y, int c) {
		charList.get(y)[x] = c;
	}

	public void isValid(Blob b) {
		for (int y = b.y; y <= b.y2; y++) {
			for (int x = b.x; x <= b.x2; x++) {
				int current = get(x, y);
				if (current == 0 || current == b.id) {
					set(x, y, b.id);
				} else {
					b.setValid(false);
					Blob other = Blob.blobMap.get(current);
					other.setValid(false);
					// break;
				}
			}
		}
	}

	public void printBoard() {
		System.out.println("");

		for (int y = 0; y < this.l; y++) {
			for (int x = 0; x < this.w; x++) {
				int i = get(x, y);
				System.out.print(i == 0 ? " " : i);
			}
			System.out.println("");
		}
		System.out.println("");
	}

	public static void print(Object o) {
		System.out.println(o);
	}

	public static class Blob {
		private static int blobCount = 2;
		private static HashMap<Integer, Blob> blobMap = new HashMap<Integer, Even.Blob>();

		final int id;
		int x = Integer.MAX_VALUE;
		int y = Integer.MAX_VALUE;
		int x2 = -1;
		int y2 = -1;
		int count = 0;
		private boolean valid = true;;

		public Blob() {
			this.id = ++blobCount;
		}

		public void setValid(boolean b) {
			valid = b;
		}

		public boolean isValid() {
			if (count <= 0 || !valid) {
				return false;
			}
			return true;
		}

		public void update(int xa, int ya) {
			count++;
			if (xa < x) {
				x = xa;
			}
			if (xa > x2) {
				x2 = xa;
			}
			if (ya < y) {
				y = ya;
			}
			if (ya > y2) {
				y2 = ya;
			}
		}

		@Override
		public String toString() {
			return "Blob-" + id + ": " + (x + 1) + ":" + (y + 1) + ", " + (x2 + 1) + ":" + (y2 + 1);
		}

		public static void print() {
			for (Blob b : blobMap.values()) {
				if (b.isValid()) {
					System.out.println(b);
				}
			}
		}

		public static Blob create() {
			Blob b = new Blob();

			blobMap.put(b.id, b);
			return b;
		}
	}

	public static void main(String[] args) {

		final Scanner sc = new Scanner(System.in);
		Even e = new Even();
		String line = null;

		while (sc.hasNextLine() && (line = sc.nextLine()) != null && !line.trim().equals("")) {
			print(line);
			e.loadLine(line);
		}
		e.printBoard();

		e.solve();
		e.printBoard();

		Blob.print();

	}

}
