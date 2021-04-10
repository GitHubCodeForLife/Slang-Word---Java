package slang;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class SlangWord {
	private TreeMap<String, String> map = new TreeMap<String, String>();
	private static SlangWord obj = new SlangWord();// Early, instance will be created at load time

	private SlangWord() {
		try {
			readFile("slangword.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static SlangWord getInstance() {
		if (obj == null) {
			synchronized (SlangWord.class) {
				if (obj == null) {
					obj = new SlangWord();// instance will be created at request time
				}
			}
		}
		return obj;
	}

	void saveFile(String file) {
		try {
			PrintWriter printWriter = new PrintWriter(new File(file));
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder.append("Slag`Meaning\n");
			String s[][] = new String[map.size()][3];
			Set<String> keySet = map.keySet();
			Object[] keyArray = keySet.toArray();
			for (int i = 0; i < map.size(); i++) {
				Integer in = i + 1;
				s[i][0] = in.toString();
				s[i][1] = (String) keyArray[i];
				s[i][2] = map.get(keyArray[i]);
				stringBuilder.append(s[i][1] + "`" + s[i][2] + "\n");
			}
			// System.out.println(stringBuilder.toString());
			printWriter.write(stringBuilder.toString());
			printWriter.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

	void readFile(String file) throws Exception {
		map.clear();
		String slag = null;
		Scanner scanner = new Scanner(new File(file));
		scanner.useDelimiter("`");
		scanner.next();
		String temp = scanner.next();
		String[] part = temp.split("\n");
		int i = 0;
		while (scanner.hasNext()) {
			slag = part[1];
			temp = scanner.next();
			part = temp.split("\n");
			System.out.println(slag + ' ' + part[0]);
			map.put(slag, part[0]);
			i++;
		}
		System.out.println(i);
		scanner.close();
	}

	public String[][] getData() {
		String s[][] = new String[map.size()][3];
		int index = 0;
		Set<String> keySet = map.keySet();
		Object[] keyArray = keySet.toArray();
		for (int i = 0; i < map.size(); i++) {
			Integer in = i + 1;
			s[i][0] = in.toString();
			s[i][1] = (String) keyArray[i];
			s[i][2] = map.get(keyArray[i]);
		}
		return s;
	}

	public String getMeaning(String key) {
		return map.get(key);
	}

	public void set(String key, String value) {
		map.put(key, value);
		this.saveFile("slangword.txt");
	}

	public void saveHistory(String slag) throws Exception {
		// String file = "history.txt";
		File file1 = new File("history.txt");
		FileWriter fr = new FileWriter(file1, true);
		fr.write(slag + "\n");
		fr.close();
	}

	public String[][] readHistory() {

		List<String> history = new ArrayList<>();
		try {
			Scanner scanner = new Scanner(new File("history.txt"));
			scanner.useDelimiter("\n");
			while (scanner.hasNext()) {
				String temp = scanner.next();
				history.add(temp);
			}
			scanner.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int size = history.size();
		String s[][] = new String[size][2];
		for (int i = 0; i < size; i++) {
			s[size - i - 1][0] = String.valueOf(size - i);
			s[size - i - 1][1] = history.get(i);
		}
		return s;
	}

	public String[][] findContain(String key) {
		// Get all slang contain key
		List<String> keyList = new ArrayList<>();
		List<String> valueList = new ArrayList<>();
		for (String keyAll : map.keySet()) {
			if (keyAll.contains(key)) {
				keyList.add(keyAll);
				valueList.add(map.get(keyAll));
				// System.out.println(keyAll + " " + map.get(keyAll));
			}
		}
		int size = keyList.size();
		String s[][] = new String[size][2];
		for (int i = 0; i < size; i++) {
			s[i][0] = keyList.get(i);
			s[i][1] = valueList.get(i);
		}
		return s;
	}

	public void reset() {
		try {

			readFile("slang-goc.txt");
			this.saveFile("slangword.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete(String key) {
		map.remove(key);
		this.saveFile("slangword.txt");
	}

	public String add(String key, String value) {
		return map.put(key, value);
	}

	public boolean checkSlang(String slag) {
		return map.containsKey(slag);
	}

	public String[] random() {
		// Random a number
		int minimun = 0;
		int maximun = map.size() - 1;
		int rand = randInt(minimun, maximun);
		// Get slang meaning
		String s[] = new String[2];
		int index = 0;
		for (String key : map.keySet()) {
			// System.out.println(key);
			if (index == rand) {
				s[0] = key;
				s[1] = map.get(key);
				break;
			}
			index++;
		}
		return s;
	}

	public static int randInt(int minimum, int maximum) {
		return (minimum + (int) (Math.random() * maximum));
	}

	public String[] quiz(int type) {
		String s[] = new String[6];
		if (type == 1) {
			// Random a number
			String[] slangRandom = this.random();
			s[0] = slangRandom[0];
			int rand = randInt(1, 4);
			s[rand] = slangRandom[1];
			s[5] = slangRandom[1];
			for (int i = 1; i <= 4; i++) {
				if (rand == i)
					continue;
				else {
					String[] slangRand = this.random();
					while (slangRand[0] == s[0]) {
						slangRand = this.random();
					}
					s[i] = slangRand[1];
				}
			}
		} else {
			// Random a number
			String[] slangRandom = this.random();
			s[0] = slangRandom[1];
			int rand = randInt(1, 4);
			s[rand] = slangRandom[0];
			s[5] = slangRandom[0];
			for (int i = 1; i <= 4; i++) {
				if (rand == i)
					continue;
				else {
					String[] slangRand = this.random();
					while (slangRand[0] == s[0]) {
						slangRand = this.random();
					}
					s[i] = slangRand[0];
				}
			}
		}

		return s;
	}
}
