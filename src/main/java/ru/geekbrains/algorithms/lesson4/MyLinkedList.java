package ru.geekbrains.algorithms.lesson4;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements Iterable<T> {

	private Node first;
	private Node last;
	private int size;

	private int changeCount;

	public MyLinkedList() {
		first = null;
		last = null;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iter();
	}

	public ListIterator<T> listIterator() {
		return new ListIter();
	}

	private class ListIter implements ListIterator<T> {
		Node current = new Node(null, first);
		int index = 0;

		@Override
		public boolean hasNext() {
			return current.getNext() != null;
		}

		@Override
		public T next() {
			current = current.getNext();
			T value = current.getValue();
			index++;
			return value;
		}

		@Override
		public boolean hasPrevious() {
			return current.getPrev() != null;
		}

		@Override
		public T previous() {
			T value = current.getValue();
			current = current.getPrev();
			index--;
			return value;
		}

		@Override
		public int nextIndex() {
			return hasNext() ? index : size;
		}

		@Override
		public int previousIndex() {
			return hasPrevious() ? index - 1 : 0;
		}

		@Override
		public void remove() {
			MyLinkedList.this.remove(current.getValue());
			if (index != 0) {
				index--;
			}
		}

		@Override
		public void set(T t) {
			current.setValue(t);
		}

		@Override
		public void add(T t) {
			insert(index, t);
		}
	}

	private class Iter implements Iterator<T> {

		Node current = new Node(null, first);
		int iterChangeCount = changeCount;

		@Override
		public boolean hasNext() {
			return current.getNext() != null;
		}

		@Override
		public T next() {
			current = current.getNext();
			return current.getValue();
		}

		@Override
		public void remove() {
			if (iterChangeCount != changeCount) {
				throw new ConcurrentModificationException();
			}
			MyLinkedList.this.remove(current.getValue());
			iterChangeCount++;
		}
	}

	private class Node {
		T value;
		Node next;
		Node prev;

		public Node(T value) {
			this.value = value;
		}

		public Node(T value, Node next) {
			this.value = value;
			this.next = next;
		}

		public Node(T value, Node next, Node prev) {
			this.value = value;
			this.next = next;
			this.prev = prev;
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

		public Node getPrev() {
			return prev;
		}

		public void setPrev(Node prev) {
			this.prev = prev;
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
		if (isEmpty()) {
			last = newNode;
		} else {
			newNode.setNext(first); // текущий first
			first.setPrev(newNode);
		}
		first = newNode; // first обновился
		size++;
		changeCount++;
	}

	public void insertLast(T item) {
		Node newNode = new Node(item);
		if (isEmpty()) {
			first = newNode;
		} else {
			newNode.setPrev(last);
			last.setNext(newNode);
		}
		last = newNode;
		size++;
		changeCount++;
	}

	public T getFirst() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return first.getValue();
	}

	public T getLast() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return last.getValue();
	}

	public T removeFirst() {
		T oldFirst = getFirst();
		first = first.getNext();
		if (isEmpty()) {
			last = null; // утечка
		} else {
			first.setPrev(null);
		}
		size--;
		changeCount++;
		return oldFirst;
	}

	public T removeLast() { // сложность - утечка памяти!!!
		T oldLast = getLast();
		if (last.getPrev() != null) {
			last.getPrev().setNext(null);
		} else {
			first = null;
		}
		last = last.getPrev();
		size--;
		changeCount++;
		return oldLast;
	}

	public final int indexOf(T item) {
		Node current = first;
		int index = 0;
		while (current != null) {
			if (current.getValue().equals(item)) {
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

	public void insert(int index, T item) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException();
		}
		if (index == 0) {
			insertFirst(item);
			return;
		}
		if (index == size) {
			insertLast(item);
			return;
		}
		Node current = first;
		int i = 0;
		while (i < index - 1) {
			current = current.getNext();
			i++;
		}
		Node newNode = new Node(item);
		newNode.setNext(current.getNext());
		newNode.setPrev(current);
		current.setNext(newNode);
		newNode.getNext().setPrev(newNode);
		size++;
		changeCount++;
	}

	public T remove(int index) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException();
		}
		if (index == 0) {
			return removeFirst();
		}
		if (index == size - 1) {
			return removeLast();
		}
		Node current = first;
		int i = 0;
		while (i < index) {
			current = current.getNext();
			i++;
		}
		T temp = current.getValue();
		current.getPrev().setNext(current.getNext());
		current.getNext().setPrev(current.getPrev());
		size--;
		changeCount++;
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
		while (current != null && !current.getValue().equals(item)) {
			current = current.getNext();
		}
		if (current == null) {
			return false;
		}
		if (current == last) {
			removeLast();
			return true;
		}
		current.getPrev().setNext(current.getNext());
		current.getNext().setPrev(current.getPrev());
		size--;
		changeCount++;
		return true;
	}

	public void clear() {
		first = null;
		last = null;
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
