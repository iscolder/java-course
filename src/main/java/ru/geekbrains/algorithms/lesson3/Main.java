package ru.geekbrains.algorithms.lesson3;

import java.util.Comparator;

public class Main {


	public static void main(String[] args) {

		MyStack<Integer> stack = new MyStack<>(1);
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);

		System.out.println(stack.peek());

		for (int i = 0; i < 4; i++) {
			System.out.println(stack.pop());
		}


		MyQueue<Integer> queue = new MyQueue<>(5);

		System.out.println(queue);


		queue.insert(5);
		queue.insert(3);
		queue.insert(2);

		System.out.println(queue.remove());

		System.out.println(queue);


		MyPriorityQueue<Integer> priorityQueue = new MyPriorityQueue<Integer>(Comparator.reverseOrder());

		priorityQueue.insert(1);
		priorityQueue.insert(6);
		priorityQueue.insert(2);
		priorityQueue.insert(8);
		priorityQueue.insert(0);


		for (int i = 0; i < 5; i++) {
			System.out.println(priorityQueue.remove());
		}


	}
}
