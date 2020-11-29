package ru.geekbrains.algorithms.lesson3;

import java.util.Arrays;
import java.util.NoSuchElementException;

@SuppressWarnings("ALL")
public class MyQueue<T> {

	private T[] list;
	private int size; // сколько элементов уже находится в стеке

	private final int DEFAULT_CAPACITY = 10;

	private int begin;
	private int end;

	public MyQueue(int capacity) {
		if (capacity <= 0) {
			throw new IllegalArgumentException("capacity: " + capacity);
		}
		list = (T[]) new Object[capacity];
	}

	public MyQueue() {
		list = (T[]) new Object[DEFAULT_CAPACITY];
	}

	public T peekFront() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return list[begin];
	}

	public T remove() {
		T temp = peekFront();
		size--;
		list[begin] = null;
		begin = nextIndex(begin);
		return temp;
	}

	public void insert(T item) {
		if (isFull()) {
			throw new IllegalStateException();
		}
		size++;
		list[end] = item;
		end = nextIndex(end);
	}

	private int nextIndex(int index) {
		return (index + 1) % list.length;
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

	@Override
	public String toString() {
		return Arrays.toString(list) + " begin: " + begin + " end: " + end;
	}
}
