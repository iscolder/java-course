package ru.geekbrains.algorithms.lesson6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
	public static void main(String[] args) {
		final int NUMBER = 20;
		final int HEIGHT = 6;
		Random random = new Random();
		List<MyTreeMap<Integer, Integer>> result = new ArrayList<>();
		for (int i = 0; i < NUMBER; i++) {
			MyTreeMap<Integer, Integer> treeMap = new MyTreeMap<>();
			while (treeMap.height() != HEIGHT) {
				int value = random.nextInt(201) - 100;
				treeMap.put(value, value);
			}
			result.add(treeMap);
		}
		System.out.println(100d * result.stream().filter(MyTreeMap::isBalanced).count() / NUMBER + " % is balanced");
	}

}
