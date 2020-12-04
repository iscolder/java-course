package ru.geekbrains.algorithms.lesson4;

import java.util.NoSuchElementException;

public class MyOneLinkedList<T> {

	private Node first; // link to first element
	private int size;

	public MyOneLinkedList() {
		first = null;
	}

	private class Node {
		T value;
		Node next;

		public Node(T value) {
			this.value = value;
		}

		public Node(T value, Node next) {
			this.value = value;
			this.next = next;
		}

		public T getValue() {
			return value;
		}

		public void setValue(T value) {
			this.value = value;
		}

		public Node getNext() {
			return next;
		}

		public void setNext(Node next) {
			this.next = next;
		}
	}

	public boolean isEmpty() {
		return first == null;
	}

	public int size() {
		return size;
	}

	public void insertFirst(T item) {
		Node newNode = new Node(item);
		newNode.setNext(first);
		first = newNode;
		size++;
	}

	public T getFirst() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return first.getValue();
	}

	public T removeFirst() {
		T old = getFirst();
		first = first.getNext();
		size--;
		return old;
	}

	public final int indexOf(T value) {
		Node current = first;
		int index = 0;
		while (current != null) {
			if (current.getValue().equals(value)) {
				return index;
			}
			current = current.getNext();
			index++;
		}
		return -1;
	}

	public boolean contains(T item) {
		return indexOf(item) > -1;
	}

	public void insert(int index, T value) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException();
		}
		if (index == 0) {
			insertFirst(value);
			return;
		}
		Node current = first;
		int i = 0;
		while (i < index - 1) {
			current = current.getNext();
			i++;
		}
		Node newNode = new Node(value);
		newNode.setNext(current.getNext());
		current.setNext(newNode);
		size++;
	}

	public T remove(int index) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException();
		}
		if (index == 0) {
			return removeFirst();
		}
		Node current = first;
		int i = 0;
		while (i < index - 1) {
			current = current.getNext();
			i++;
		}
		T temp = current.getNext().getValue();
		current.setNext(current.getNext().getNext());
		size--;
		return temp;
	}

	public boolean remove(T item) {
		if (isEmpty()) {
			return false;
		}
		if (first.getValue().equals(item)) {
			removeFirst();
			return true;
		}
		Node current = first;
		while (current.getNext() != null && !current.getNext().getValue().equals(item)) {
			current = current.getNext();
		}
		if (current.getNext() == null) {
			return false;
		}
		current.setNext(current.getNext().getNext());
		size--;
		return true;
	}
	
	public void clear() {
		first = null;
		size = 0;
	}

	@Override
	public String toString() {
		Node current = first;
		StringBuilder sb = new StringBuilder();
		while (current != null) {
			sb.append(current.getValue()).append(", ");
			current = current.getNext();
		}
		if (sb.length() != 0) {
			sb.setLength(sb.length() - 2);
		}
		return sb.toString();
	}
}
