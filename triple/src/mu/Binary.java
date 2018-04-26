package mu;

import java.util.ArrayList;

public class Binary {

	public static boolean binary(ArrayList<String> list, String word) {
		return recurse(0, list.size(), list, word);
	}

	private static boolean recurse(int i, int j, ArrayList<String> list, String word) {
		int middle = (i + j) / 2;
		if (i == j) {
			return false;
		}
		String m = list.get(middle);
		if (m.equals(word)) {
			return true;
		} else {
			if (m.compareTo(word) > 0) {
				return recurse(i, middle, list, word);
			}
			return recurse(middle + 1, j, list, word);
		}
	}

	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<>();
		list.add("alpha");
		list.add("bravo");
		list.add("charlie");
		list.add("echo");

		System.out.println(binary(list, "alpha"));
		System.out.println(binary(list, "bravo"));
		System.out.println(binary(list, "charlie"));
		System.out.println(binary(list, "echo"));
		System.out.println(binary(list, "foos"));
		System.out.println(binary(list, "a"));
	}
}
