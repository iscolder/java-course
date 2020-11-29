package ru.geekbrains.algorithms.lesson3;

import java.util.Arrays;
import java.util.EmptyStackException;

@SuppressWarnings("ALL")
public class MyStack<T> {

	private T[] list;
	private int size; // сколько элементов уже находится в стеке

	private final int DEFAULT_CAPACITY = 10;

	public MyStack(int capacity) {
		if (capacity <= 0) {
			throw new IllegalArgumentException("capacity: " + capacity);
		}
		list = (T[]) new Object[capacity];
	}

	public MyStack() {
		list = (T[]) new Object[DEFAULT_CAPACITY];
	}

	public T peek() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		return list[size - 1];
	}

	public T pop() {
		T temp = peek();
		size--;
		list[size] = null;
		return temp;
	}

	public void push(T item) {
		if (isFull()) {
			reCapacity(DEFAULT_CAPACITY);
		}
		list[size] = item;
		size++;
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

	private void reCapacity(int newCapacity) {
		T[] temp = (T[]) new Object[newCapacity];
		System.arraycopy(list, 0, temp, 0, size);
		list = temp;
	}

}
