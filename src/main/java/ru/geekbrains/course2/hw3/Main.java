package ru.geekbrains.course2.hw3;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {
		Map<String, Long> uniquesWords = countUniqueWords(new String[]{"a", "b", "a", "a", "a", "l", "m", "m", "o", "", null, null});
		System.out.println(uniquesWords);

		PhoneDictionary dictionary = new PhoneDictionary();
		dictionary.add("Ivan", "111-11-11");
		dictionary.add("Anna", "222-22-22");
		dictionary.add("Kostya", "333-33-33");
		dictionary.add("Ura", "444-44-44");
		dictionary.add("Sergey", "555-55-55");
		dictionary.add("Sergey", "666-66-66");
		dictionary.add("Anna", "777-77-77");
		dictionary.add("Anna", "888-88-88");
		dictionary.add("Ura", "999-99-99");
		dictionary.add(null, "000-00-00");

		System.out.println("Anna: " + dictionary.get("Anna"));
		System.out.println("Ivan: " + dictionary.get("Ivan"));
		System.out.println("Kostya: " + dictionary.get("Kostya"));
		System.out.println("Sergey: " + dictionary.get("Sergey"));
		System.out.println("Ura: " + dictionary.get("Ura"));
		System.out.println("Null: " + dictionary.get(null));
		System.out.println("Anton: " + dictionary.get("Anton"));
	}

	private static Map<String, Long> countUniqueWords(String[] words) {
		return words == null ? new HashMap<>() : Arrays.stream(words).filter(Objects::nonNull).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}

	private static class PhoneDictionary {
		private final Map<String, List<String>> dictionary = new HashMap<>();

		void add(String personName, String phoneNumber) {
			if (!dictionary.containsKey(personName)) {
				dictionary.put(personName, new ArrayList<>());
			}
			dictionary.get(personName).add(phoneNumber);
		}

		List<String> get(String personName) {
			return dictionary.get(personName);
		}
	}

}
