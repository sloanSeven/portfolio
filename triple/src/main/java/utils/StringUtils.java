package utils;

public class StringUtils {

	public static String toString(boolean[] arr, int b, int base) {
		StringBuilder sb = new StringBuilder();
		for (int i = arr.length - 1; i >= 0; i--) {
			sb.append(arr[i] ? "1" : "0");
		}
		String s = sb.toString();
		Integer i = Integer.valueOf(s, b);
		// System.out.println(b + ": " + i);

		String result = Integer.toString(i, base);// i.toString();
		// System.out.println(base + ": " + result);
		return result;
	}

}
