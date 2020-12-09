package ru.geekbrains.algorithms.lesson6;

public class Main {

	public static void main(String[] args) {

		MyTreeMap<Integer, String> myTreeMap = new MyTreeMap<>();

		myTreeMap.put(5, "five");
		myTreeMap.put(1, "one");
		myTreeMap.put(2, "two");
		myTreeMap.put(3, "three");
		myTreeMap.put(2, "2");

		System.out.println(myTreeMap.size());
		System.out.println(myTreeMap);


		myTreeMap.deleteMin();

		System.out.println(myTreeMap);


	}

}
