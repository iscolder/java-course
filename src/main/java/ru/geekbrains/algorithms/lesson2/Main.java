package ru.geekbrains.algorithms.lesson2;

import java.util.Comparator;
import java.util.Random;

public class Main {


	public static void main(String[] args) {

		MyArrayList<Integer> mal = new MyArrayList<>();
		Random random = new Random();

		for (int i = 0; i < 100; i++) {
			mal.add(random.nextInt(1000));
		}

		mal.selectionSort(Comparator.reverseOrder());

		System.out.println(mal);

		mal.insertionSort(Comparator.naturalOrder());

		System.out.println(mal);

		mal.bubbleSort(Comparator.reverseOrder());

		System.out.println(mal);

	}


}
