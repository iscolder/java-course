package ru.geekbrains.algorithms.lesson4;

public class MyNewStack<T> {

	private final MyLinkedList<T> list;

	public MyNewStack() {
		list = new MyLinkedList<>();
	}

	public T peek() {
		return list.getFirst();
	}

	public T pop() {
		return list.removeFirst();
	}

	public void push(T item) {
		list.insertFirst(item);
	}

	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}


}
