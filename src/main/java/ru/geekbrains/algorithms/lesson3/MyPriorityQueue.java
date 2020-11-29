package ru.geekbrains.algorithms.lesson3;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EmptyStackException;


@SuppressWarnings("unchecked")
public class MyPriorityQueue<T extends Comparable<T>> {

	private T[] list;
	private int size; // сколько элементов уже находится в стеке

	private final int DEFAULT_CAPACITY = 10;

	private final Comparator<T> comparator;

	public MyPriorityQueue(int capacity) {
		this(capacity, Comparator.naturalOrder());
	}

	public MyPriorityQueue() {
		this(Comparator.naturalOrder());
	}

	public MyPriorityQueue(int capacity, Comparator<T> comparator) {
		if (capacity <= 0) {
			throw new IllegalArgumentException("capacity: " + capacity);
		}
		list = (T[]) new Comparable[capacity];
		this.comparator = comparator;
	}

	public MyPriorityQueue(Comparator<T> comparator) {
		this.comparator = comparator;
		list = (T[]) new Comparable[DEFAULT_CAPACITY];
	}

	public T peek() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		return list[size - 1];
	}

	public T remove() {
		T temp = peek();
		size--;
		list[size] = null;
		return temp;
	}

	public void insert(T item) {
		if (isFull()) {
			throw new StackOverflowError();
		}
		list[size] = item;
		size++;

		int i = size - 1;

		while (i > 0 && comparator.compare(list[i], list[i - 1]) > 0) {
			swap(i, i - 1);
			i--;
		}

	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean isFull() {
		return size == list.length;
	}

	private void swap(int index1, int index2) {
		T temp = list[index1];
		list[index1] = list[index2];
		list[index2] = temp;
	}

}
