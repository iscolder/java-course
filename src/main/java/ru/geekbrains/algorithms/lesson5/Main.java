package ru.geekbrains.algorithms.lesson5;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

	private static final int MAX_WEIGHT = 20;
	private static List<Item> bestItems = new ArrayList<>();

	public static void main(String[] args) {
		Item[] items = {
				new Item(1, 1, 1),
				new Item(2, 2, 3),
				new Item(3, 2, 2),
				new Item(4, 4, 5),
				new Item(5, 5, 4),
				new Item(6, 3, 3)
		};

		permutation(items, 0);

		System.out.println("Best set: " + bestItems);
	}

	private static class Item {
		int id;
		int weight;
		int price;

		public Item(int id, int weight, int price) {
			this.id = id;
			this.weight = weight;
			this.price = price;
		}

		public int getWeight() {
			return weight;
		}

		public int getPrice() {
			return price;
		}

		@Override
		public String toString() {
			return "Item{" +
					"id=" + id +
					", weight=" + weight +
					", price=" + price +
					'}';
		}
	}

	private static void swap(Item[] c, int pos1, int pos2) {
		Item temp = c[pos1];
		c[pos1] = c[pos2];
		c[pos2] = temp;
	}

	// not optimal
	public static void permutation(Item[] items, int start) {
		List<Item> temp = new ArrayList<>();
		for (int i = 0; i < start; i++) {
			temp.add(items[i]);
			System.out.println(temp);
		}

		if (fitWeight(temp) && getTotalPrice(temp) > getTotalPrice(bestItems)) {
			bestItems = temp;
		}

		for (int i = start; i < items.length; i++) {
			swap(items, start, i);
			permutation(items, start + 1);
			swap(items, start, i);
		}
	}

	private static boolean fitWeight(List<Item> items) {
		return items.stream().collect(Collectors.summarizingInt(Item::getWeight)).getSum() <= MAX_WEIGHT;
	}

	private static long getTotalPrice(List<Item> items) {
		return items.stream().collect(Collectors.summarizingInt(Item::getPrice)).getSum();
	}


	static int factorial(int n) {
		if (n <= 1) {
			return 1;
		}
		return factorial(n - 1) * n;
	}


	static long fibo(int n) {
		long a = 1;
		long b = 1;

		for (int i = 3; i <= n; i++) {
			b = a + b;
			a = b - a;
		}

		return b;
	}

	static long recFibo(int n) {
		if (n <= 2) {
			return 1;
		}
		return recFibo(n - 2) + recFibo(n - 1);
	}

	static int triangle(int n) {
		int sum = 0;
		for (int i = 0; i <= n; i++) {
			sum += i;
		}
		return sum;
	}

	static int recTriangle(int n) {
		if (n == 1) {
			return 1;
		}
		return recTriangle(n - 1) + n;
	}

	static int multiply(int n, int d) {
		if (n == 1) {
			return d;
		}
		return multiply(n - 1, d) + d;
	}

}
