package mighty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

import utils.FileUtils;

public class MightySolution {

	private FileUtils utils;
	private HashMap<String, Person> idMap = new HashMap<>();

	public static void p(Object o) {
		System.out.println(o);
	}

	public MightySolution(String file) {
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

	public Person createPerson(String[] values) {
		Person p = new Person(generateId());
		for (String key : values) {
			p.addIdentifier(key);
			idMap.put(key, p);
		}
		p.addIdentifier(p.getId());
		idMap.put(p.getId(), p);
		return p;
	}

	public Person getPerson(String key) {
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

		final ArrayList<Person> refList = new ArrayList<>();

		for (String s : values) {
			if ("".equals(s)) {
				continue;
			}
			Person person = getPerson(s);
			if (person != null && !refList.contains(person)) {
				refList.add(person);
			}
		}

		final Person original = (refList.isEmpty()) ? createPerson(values) : refList.get(0);
		for (String key : values) {
			original.addIdentifier(key);
		}

		for (Person person : refList) {
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

		final String[] split = args.split(",");
		final String header = utils.readLine();
		final Integer[] match = findIndexes(header, split);
		final String[] valueArray = new String[match.length];
		final ArrayList<String> lineList = new ArrayList<>();
		final String output = "output.csv";

		String line = null;
		while ((line = utils.readLine()) != null) {
			parseIdentifiers(line, match, valueArray);
			Person p = identify(valueArray);
			lineList.add(p.getId());
			lineList.add(line);
		}
		utils.write(output, "uuid," + header + "\n");
		for (int i = 0; i < lineList.size(); i += 2) {
			Person person = getPerson(lineList.get(i));
			utils.write(output, (person.getId() + "," + lineList.get(i + 1)) + "\n");
		}
	}

	public static void main(String[] args) {
		MightySolution sol = new MightySolution(args[0]);
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
