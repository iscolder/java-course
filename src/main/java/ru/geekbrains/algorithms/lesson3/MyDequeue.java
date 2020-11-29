package ru.geekbrains.algorithms.lesson3;

import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class MyDequeue<T> {
	private final T[] list;
	private int size;

	private static final int DEFAULT_CAPACITY = 10;

	private int start;
	private int end;
	private int reverseStart;
	private int reverseEnd;

	public MyDequeue() {
		list = (T[]) new Object[DEFAULT_CAPACITY];
	}

	public MyDequeue(int capacity) {
		if (capacity <= 0) {
			throw new IllegalArgumentException("capacity: " + capacity);
		}
		list = (T[]) new Object[capacity];
	}

	public T peek() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return list[start];
	}

	public void insert(T item) {
		if (isFull()) {
			throw new StackOverflowError();
		}
		size++;
		list[end] = item;
		reverseStart = end;
		reverseEnd = previousIndex(start);
		end = nextIndex(end);
	}

	public T pop() {
		T temp = peek();
		size--;
		list[start] = null;
		reverseEnd = start;
		start = nextIndex(start);
		return temp;
	}

	public T peekReverse() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return list[reverseStart];
	}

	public void insertReverse(T item) {
		if (isFull()) {
			throw new StackOverflowError();
		}
		size++;
		list[reverseEnd] = item;
		start = reverseEnd;
		end = nextIndex(reverseStart);
		reverseEnd = previousIndex(reverseEnd);
	}

	public T popReverse() {
		T temp = peekReverse();
		size--;
		list[reverseStart] = null;
		end = reverseStart;
		reverseStart = previousIndex(reverseStart);
		return temp;
	}

	private int nextIndex(int index) {
		return index == list.length - 1 ? 0 : index + 1;
	}

	private int previousIndex(int index) {
		return index != 0 ? index - 1 : list.length - 1;
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
		return arrayToString() + ", start=" + start + ", end=" + end + ", reverse start=" + reverseStart + ", reverse end=" + reverseEnd;
	}

	private String arrayToString() {
		if (isEmpty()) return "[]";
		StringBuilder sb = new StringBuilder("[");
		for (T e : list) {
			sb.append(e).append(", ");
		}
		sb.setLength(sb.length() - 2);
		sb.append("]");
		return sb.toString();
	}

}
