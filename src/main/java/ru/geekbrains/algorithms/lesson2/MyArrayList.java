package ru.geekbrains.algorithms.lesson2;

import java.util.Comparator;

@SuppressWarnings("ALL")
public class MyArrayList<T extends Comparable<T>> {
	private T[] list;
	private int size;

	private static final int DEFAULT_CAPACITY = 10;


	public MyArrayList(int capacity) {
		if (capacity <= 0) {
			throw new IllegalArgumentException("capacity: " + capacity);
		}

		list = (T[]) new Comparable[capacity];

	}

	public MyArrayList() {
		list = (T[]) new Comparable[DEFAULT_CAPACITY];
	}

	public void add(T item) {
		list[size] = item;
		size++;
	}

	public void add(int index, T item) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException("index: " + index);
		}
		for (int i = size; i > index; i--) {
			list[i] = list[i - 1];
		}
		list[index] = item;
		size++;
	}

	public void remove(int index) {
		checkIndex(index);
		for (int i = index; i < size - 1; i++) {
			list[i] = list[i + 1];
		}
		size--;
		list[size] = null; // !!!
	}

	public int size() {
		return size;
	}

	public final int indexOf(T item) {
		for (int i = 0; i < size; i++) {
			if (list[i].equals(item)) {
				return i;
			}
		}
		return -1;
	}

	public boolean remove(T item) {
		int i = indexOf(item);
		if (i == -1) {
			return false;
		}
		remove(i);
		return true;
	}

	public T get(int index) {
		checkIndex(index);
		return list[index];
	}

	public void set(int index, T item) {
		checkIndex(index);
		list[index] = item;
	}

	private void checkIndex(int index) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException("index: " + index);
		}
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");

		for (int i = 0; i < size; i++) {
			sb.append(list[i]).append(", ");
		}

		if (size > 0) {
			sb.setLength(sb.length() - 2);
		}

		sb.append("]");

		return sb.toString();
	}


	private boolean less(T item1, T item2) {
		return item1.compareTo(item2) < 0;
	}

	private void swap(int index1, int index2) {
		T temp = list[index1];
		list[index1] = list[index2];
		list[index2] = temp;
	}

	public void selectionSort() {
		int iMin;
		for (int i = 0; i < size - 1; i++) {
			iMin = i;
			for (int j = i + 1; j < size; j++) {
				if (less(list[j], list[iMin])) {
					iMin = j;
				}
			}
			swap(i, iMin);
		}
	}

	public void selectionSort(Comparator<T> comparator) {
		int iMin;
		for (int i = 0; i < size - 1; i++) {
			iMin = i;
			for (int j = i + 1; j < size; j++) {
				if (comparator.compare(list[j], list[iMin]) < 0) {
					iMin = j;
				}
			}
			swap(i, iMin);
		}
	}



	// Good sort
	public void insertionSort() {
		T key;
		for (int i = 1; i < size; i++) {
			int j = i;
			key = list[i];
			while (j > 0 && less(key, list[j - 1])) {
				list[j] = list[j - 1];
				j--;
			}
			list[j] = key;
		}
	}

	public void bubbleSort() {
		boolean isSwap;
		for (int i = size - 1; i > 0; i--) {
			isSwap = false;
			for (int j = 0; j < i; j++) {
				if (less(list[j + 1], list[j])) {
					swap(j, j + 1);
					isSwap = true;
				}
			}
			if (!isSwap) {
				break;
			}
		}
	}

	private void quickSort(int lo, int hi) {
		if (lo >= hi) { //базовый случай
			return;
		}
		int mid = lo + (hi - lo) / 2;
		T opora = list[mid];
		int i = lo;
		int j = hi;
		while (i <= j) {
			while (less(list[i], opora)) {
				i++;
			}
			while (less(opora, list[j])) {
				j--;
			}
			if (i <= j) {
				swap(i, j);
				i++;
				j--;
			}
		}
		if (lo < j) { // рекурентный случай
			quickSort(lo, j);
		}
		if (hi > i) {
			quickSort(i, hi);
		}
	}

	public void qSort() {
		quickSort(0, size - 1);
	}

}
