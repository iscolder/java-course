package ru.geekbrains.course3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) {
		Integer[] array = new Integer[]{1, 2, 3};
		swap(array, 0, 2);
		System.out.println("array swap = " + Arrays.toString(array));
		List<Integer> list = arrayToList(array);
		System.out.println("array to list = " + list);


		Box<Apple> appleBox = new Box<>();
		appleBox.add(new Apple());
		appleBox.add(new Apple());
		appleBox.add(new Apple());
		appleBox.add(new Apple());
		appleBox.add(new Apple());

		System.out.println("Apple Box weight = " + appleBox.getWeight());

		Box<Orange> orangeBox = new Box<>();
		orangeBox.add(new Orange());
		orangeBox.add(new Orange());
		orangeBox.add(new Orange());
		orangeBox.add(new Orange());

		System.out.println("Orange Box weight = " + appleBox.getWeight());
		System.out.println("Orange box weight equals Apple Box weight: " + orangeBox.compare(appleBox));


		Box<Apple> anotherAppleBox = new Box<>();

		appleBox.moveToNewBox(anotherAppleBox);

		System.out.println("Apple Box weight = " + appleBox.getWeight());
		System.out.println("Another Apple Box weight = " + anotherAppleBox.getWeight());
	}

	public static <T> void swap(T[] array, int pos1, int pos2) {
		if (array == null) {
			return;
		}
		if (pos1 < 0 || pos1 >= array.length) {
			return;
		}
		if (pos2 < 0 || pos2 >= array.length) {
			return;
		}
		T temp = array[pos2];
		array[pos2] = array[pos1];
		array[pos1] = temp;
	}

	public static <T> List<T> arrayToList(T[] array) {
		if (array == null) {
			return new ArrayList<>();
		}
		return Stream.of(array).collect(Collectors.toList());
	}

	public abstract static class Fruit {
		protected String name;
		protected float weight;

		public Fruit(String name, float weight) {
			this.name = name;
			this.weight = weight;
		}

		public String getName() {
			return name;
		}

		public float getWeight() {
			return weight;
		}
	}

	public static class Apple extends Fruit {
		public Apple() {
			super("apple", 1.0f);
		}
	}

	public static class Orange extends Fruit {
		public Orange() {
			super("orange", 1.5f);
		}
	}

	public static class Box<F extends Fruit> {
		private final List<F> fruits = new ArrayList<>();

		public void add(F fruit) {
			fruits.add(fruit);
		}

		public float getWeight() {
			return (float) fruits.stream().mapToDouble(Fruit::getWeight).sum();
		}

		public List<F> getFruits() {
			return fruits;
		}

		public boolean compare(Box<? extends Fruit> other) {
			if (other == null) {
				return false;
			}
			return getWeight() == other.getWeight();
		}

		public void moveToNewBox(Box<F> newBox) {
			newBox.getFruits().addAll(fruits);
			fruits.clear();
		}

	}

}
