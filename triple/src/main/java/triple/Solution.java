package triple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class Solution {

	public static LinkedList<Object> queue = new LinkedList<>();
	public static HashMap<String, Boolean> map = new HashMap<>();
	static Board b = new Board();

	public static void main(String[] args) {

		final char o = 'O';
		final Scanner scanner = new Scanner(System.in);
		String input;
		while ((input = scanner.nextLine()) != null && !input.equalsIgnoreCase("quit") && !input.equalsIgnoreCase("")) {
		}
		System.out.print("...end trans");

		scanner.close();
	}

	private static boolean process(String input) {
		try {
			String[] split = input.split(" ");
			int x = Integer.parseInt(split[0]);
			int y = Integer.parseInt(split[1]);
			return b.add(x, y, 'X');
		} catch (Exception e) {
			return false;
		}
	}

	public static class Board {

		char[][] b = new char[3][3];
		int count = 0;

		private boolean check(char c) {

			for (int i = 0; i < 3; i++) {
				if (checkRow(i, c) || checkCol(i, c)) {
					return true;
				}
				checkDiag(c);
			}
			return false;
		}

		public boolean checkDiag(char c) {
			int count = 0;
			for (int i = 0; i < 3; i++) {
				if (b[i][i] == c) {

				}
			}
			return true;
		}

		public boolean checkRow(int row, char c) {
			int count = 0;
			for (int i = 0; i < 3; i++) {
				if (b[row][i] == c) {
					count++;
				}
			}
			return count == 3;
		}

		public boolean checkCol(int row, char c) {
			int count = 0;
			for (int i = 0; i < 3; i++) {
				if (b[i][row] == c) {
					count++;
				}
			}
			return count == 3;
		}

		public boolean addO(char c) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (isOpen(i, j)) {
						add(i, j, c);
						return true;
					}

				}
			}
			return false;
		}

		public boolean isOpen(int x, int y) {
			return b[x][y] != 'X' && b[x][y] != 'O';
		}

		public boolean add(int x, int y, char c) {
			if (x < 0 || y < 0 || x >= 3 || y >= 3) {
				return false;
			}

			if (!isOpen(x, y)) {
				return false; // throw new RuntimeException("square taken");
			}
			count++;
			b[x][y] = c;
			return true;
		}

		public String toString() {
			StringBuilder buf = new StringBuilder();
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					char c = b[i][j];
					if (c != 'X' && c != 'O') {
						c = '-';
					}
					buf.append(c);
					if (j < 2) {
						buf.append("|");
					}
				}
				buf.append("\n");
			}
			return buf.toString();
		}

	}

	private static String toBase(int decNumber, int base) {
		Stack<Integer> remstack = new Stack<Integer>();
		while (decNumber > 0) {
			int rem = decNumber % 2;
			remstack.push(rem);
			decNumber = decNumber / 2;
		}

		String binString = "";
		while (!remstack.isEmpty()) {
			binString += remstack.pop();
		}
		return binString;
	}

	public class CountVisitor<K> extends AbstractVisitor<Integer, K> {

		private int count = 0;

		@Override
		public Integer result() {
			return count;
		}

		@Override
		protected void act(Node<K> node) {
			count++;
		}

	}

	public static abstract class AbstractVisitor<T, K> implements IVistor<T, K> {

		private HashMap<Node<K>, Boolean> visitMap = new HashMap<>();

		@Override
		public void apply(Node<K> node) {
			if (visitMap.containsKey(node)) {
				return;
			}
			visitMap.put(node, true);
			act(node);
		}

		protected abstract void act(Node<K> node);

	}

	public static class Node<T> {

		final ArrayList<Node<T>> childList = new ArrayList<>();
		final T value;

		public Node(T v) {
			this.value = v;
		}

		public void visit(IVistor v) {
			v.apply(this);
			for (Node<T> n : childList) {
				n.visit(v);
			}
		}
	}
}
