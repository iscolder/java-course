package ru.geekbrains.algorithms.lesson6;

import java.util.NoSuchElementException;

public class MyTreeMap<Key extends Comparable<Key>, Value> {

	private Node root;

	private class Node {
		Key key;
		Value value;
		Node right;
		Node left;
		int size;
		int height;

		public Node(Key key, Value value) {
			this.key = key;
			this.value = value;
			size = 1;
			height = 0;
		}

	}

	public boolean contains(Key key) {
		isKeyNotNull(key);
		return get(key) != null;
	}

	public Value get(Key key) {
		return get(root, key);
	}

	// наша реализациф: ключ и значение не равны null
	public void put(Key key, Value value) {
		isKeyNotNull(key);
		if (value == null) {
			// delete
			return;
		}
		root = put(root, key, value);
	}

	private Node put(Node node, Key key, Value value) {
		if (node == null) {
			return new Node(key, value);
		}
		int cmp = key.compareTo(node.key);
		if (cmp == 0) {
			node.value = value;
		} else if (cmp < 0) {
			node.left = put(node.left, key, value);
		} else {
			node.right = put(node.right, key, value);
		}
		node.size = size(node.left) + size(node.right) + 1;
		node.height = height(node.left) > height(node.right) ? height(node.left) + 1 : height(node.right) + 1;
		return node;
	}

	private Value get(Node node, Key key) {
		if (node == null) { // базовый случай
			return null;
		}
		int cmp = key.compareTo(node.key);
		if (cmp == 0) { // базовый случай
			return node.value;
		} else if (cmp < 0) {
			return get(node.left, key);
		} else {
			return get(node.right, key);
		}
	}

	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return size(root);
	}

	private int size(Node node) {
		if (node == null) {
			return 0;
		}
		return node.size;
	}

	public int height() {
		return height(root);
	}

	private int height(Node node) {
		if (node == null) {
			return 0;
		}
		return node.height;
	}

	private void isKeyNotNull(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("Key cannot be null");
		}
	}

	@Override
	public String toString() {
		return toString(root);
	}

	private String toString(Node node) {
		if (node == null) {
			return "";
		}
		return toString(node.left) + " " + node.key + " " + toString(node.right);
	}

	public Key minKey() {
		if (isEmpty()) {
			throw new NoSuchElementException("Map is null");
		}
		return min(root).key;
	}

	private Node min(Node node) {
		if (node.left == null) {
			return node;
		}
		return min(node.left);
	}

	public Key maxKey() {
		if (isEmpty()) {
			throw new NoSuchElementException("Map is null");
		}
		return max(root).key;
	}

	private Node max(Node node) {
		if (node.right == null) {
			return node;
		}
		return max(node.right);
	}

	public void deleteMax() {
		if (isEmpty()) {
			throw new NoSuchElementException("map empty");
		}
		root = deleteMax(root);
	}

	private Node deleteMax(Node node) {
		if (node.right == null) {
			return node.left;
		}
		node.right = deleteMax(node.right);
		node.size = size(node.left) + size(node.right) + 1;
		node.height = height(node.left) > height(node.right) ? height(node.left) + 1 : height(node.right) + 1;
		return node;
	}

	public void deleteMin() {
		if (isEmpty()) {
			throw new NoSuchElementException("Map is null");
		}
		root = deleteMin(root);
	}

	private Node deleteMin(Node node) {
		if (node.left == null) {
			return node.right;
		}
		node.left = deleteMin(node.left);
		node.size = size(node.left) + size(node.right) + 1;
		node.height = height(node.left) > height(node.right) ? height(node.left) + 1 : height(node.right) + 1;
		return node;
	}


	public void delete(Key key) {
		isKeyNotNull(key);
		root = delete(root, key);
	}

	private Node delete(Node node, Key key) {
		if (node == null) {
			return null;
		}
		int cmp = key.compareTo(node.key);
		if (cmp < 0) {
			node.left = delete(node.left, key);
		} else if (cmp > 0) {
			node.right = delete(node.right, key);
		} else {
			if (node.left == null) {
				return node.right;
			}
			if (node.right == null) {
				return node.left;
			}

			Node temp = node;

			node = min(node.right);

			node.right = deleteMin(temp);
			node.left = temp.left;
		}
		node.size = size(node.left) + size(node.right) + 1;
		node.height = height(node.left) > height(node.right) ? height(node.left) + 1 : height(node.right) + 1;
		return node;
	}

	public boolean isBalanced() {
		return isBalanced(root);
	}

	private boolean isBalanced(Node node) {
		if (node == null) {
			return true;
		}
		if (Math.abs(height(node.left) - height(node.right)) > 1) {
			return false;
		}
		return isBalanced(node.left) && isBalanced(node.right);
	}

}
