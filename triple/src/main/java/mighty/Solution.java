package mighty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

import utils.FileUtils;

public class Solution {

	private FileUtils utils;
	private HashMap<String, Person> idMap = new HashMap<>();

	public static void p(Object o) {
		System.out.println(o);
	}

	public Solution(String file) {
		this.utils = new FileUtils(file);
	}

	private String generateId() {
		return UUID.randomUUID().toString();
	}

	private Integer[] findIndexes(String header, String... strings) {
		ArrayList<Integer> list = new ArrayList<>();
		String[] split = header.split(",");
		int i = 0;
		for (String s : split) {
			for (String type : strings) {
				if (s.toLowerCase().contains(type)) {
					list.add(i);
				}
			}
			i++;
		}
		return list.toArray(new Integer[list.size()]);
	}

	public Person createId(String[] values) {
		Person p = new Person(generateId());
		for (String key : values) {
			p.addIdentifier(key);
			if (idMap.containsKey(values)) {
				throw new RuntimeException("really?");
			}
			idMap.put(key, p);
		}
		p.addIdentifier(p.getId());
		idMap.put(p.getId(), p);
		return p;
	}

	public Person getPerson(String key) {
		if (key == null || key.trim().equals("")) {
			return null;
		}
		Person id = idMap.get(key);
		return id;
	}

	public void updatePerson(String key, Person p) {
		idMap.put(key, p);
	}

	public void parseIdentifiers(String s, Integer[] match, String[] dest) {
		String[] split = s.split(",");
		for (int i = 0; i < match.length; i++) {
			dest[i] = split[match[i]].trim();
		}
	}

	public Person identify(String[] values) {

		final ArrayList<Person> list = new ArrayList<>();

		for (String s : values) {
			if ("".equals(s)) {
				continue;
			}
			Person person = getPerson(s);
			if (person != null) {
				list.add(person);
			}
		}

		final Person original = (list.isEmpty()) ? createId(values) : list.get(0);
		for (String key : values) {
			original.addIdentifier(key);
		}

		for (Person person : list) {
			for (String key : person.getIdentifierList()) {
				if (original != person) {
					original.addIdentifier(key);
				}
				updatePerson(key, original);
			}
		}
		return original;
	}

	private void solve(String args) {

		String[] split = args.split(",");
		String line = utils.readLine();
		final Integer[] match = findIndexes(line, split);
		final String[] valueArray = new String[match.length];

		ArrayList<String> lineList = new ArrayList<>();

		while ((line = utils.readLine()) != null) {
			parseIdentifiers(line, match, valueArray);
			Person p = identify(valueArray);
			lineList.add(p.getId());
			lineList.add(line);
		}
		for (int i = 0; i < lineList.size(); i += 2) {
			Person person = getPerson(lineList.get(i));
			p(person.getId() + "," + lineList.get(i + 1));
		}
	}

	public static void main(String[] args) {
		Solution sol = new Solution(args[0]);
		sol.solve(args[1]);
	}

	private static class Person {

		final String id;
		final ArrayList<String> list = new ArrayList<>();

		public Person(String generateId) {
			id = generateId;
		}

		public void addIdentifier(String key) {
			if (!"".equals(key) && !list.contains(key)) {
				list.add(key);
			}
		}

		public ArrayList<String> getIdentifierList() {
			return list;
		}

		public String getId() {
			return id;
		}

		public String toString() {
			String s = "P:" + id + " [\n\t";
			Collections.sort(this.list);
			for (String k : list) {
				s += k + "\n\t";
			}
			return s + "]";
		}
	}

}
