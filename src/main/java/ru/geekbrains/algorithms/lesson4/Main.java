package ru.geekbrains.algorithms.lesson4;

public class Main {

	public static void main(String[] args) {
		MyLinkedList<String> mll = new MyLinkedList<>();
		mll.insertFirst("Maria");
		mll.insertFirst("Katya");
		mll.insertFirst("Luba");

		System.out.println(mll);

		mll.insert(1, "mmm");

		System.out.println(mll);

		mll.remove(1);

		System.out.println(mll);

		for (String s : mll) {
			System.out.println(s);
		}

	}
}
