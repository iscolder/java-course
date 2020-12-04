package ru.geekbrains.algorithms.lesson4;

import java.util.ListIterator;

public class Main {

	public static void main(String[] args) {
		MyLinkedList<String> mll = new MyLinkedList<>();
		mll.insertFirst("Maria");
		mll.insertFirst("Katya");
		mll.insertFirst("Luba");
		mll.insertFirst("Tara");

		System.out.println("Data: " + mll);

//		Iterator<String> mllIterator = mll.iterator();
//
//		while (mllIterator.hasNext()) {
//			mllIterator.next();
//			mllIterator.remove();
//		}


		//System.out.println("After Iterator remove: " + mll);

		ListIterator<String> listIterator = mll.listIterator();


		listIterator.next();
		System.out.println("next: " + listIterator.nextIndex());
		System.out.println("prev: " + listIterator.previousIndex());

		listIterator.next();
		System.out.println("next: " + listIterator.nextIndex());
		System.out.println("prev: " + listIterator.previousIndex());

		listIterator.add("Boris");

		listIterator.next();
		System.out.println("next: " + listIterator.nextIndex());
		System.out.println("prev: " + listIterator.previousIndex());



		listIterator.next();
		System.out.println("next: " + listIterator.nextIndex());
		System.out.println("prev: " + listIterator.previousIndex());

		listIterator.set("Anna");

		System.out.println(mll);





	}
}
