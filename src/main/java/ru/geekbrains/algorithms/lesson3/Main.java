package ru.geekbrains.algorithms.lesson3;

public class Main {

	public static void main(String[] args) {

		ReverseStringTransformer transformer = new ReverseStringTransformer();
		String result = transformer.transform("12345-6789");
		System.out.println("Reverse string = " + result);

		MyDequeue<Integer> queue = new MyDequeue<>(5);
		queue.insert(1);
		System.out.println(queue);
		queue.insert(2);
		System.out.println(queue);
		queue.insert(3);
		System.out.println(queue);
		queue.insert(4);
		System.out.println(queue);
		queue.insert(5);
		System.out.println(queue);

		System.out.println("Pop: " + queue.pop());
		System.out.println(queue);
		System.out.println("Pop: " + queue.pop());
		System.out.println(queue);
		System.out.println("Pop: " + queue.pop());
		System.out.println(queue);
		System.out.println("Pop: " + queue.pop());
		System.out.println(queue);
		System.out.println("Pop: " + queue.pop());
		System.out.println(queue);

		System.out.println();
		System.out.println("Reverse:");
		System.out.println();

		queue.insertReverse(1);
		System.out.println(queue);
		queue.insertReverse(2);
		System.out.println(queue);
		queue.insertReverse(3);
		System.out.println(queue);
		queue.insertReverse(4);
		System.out.println(queue);
		queue.insertReverse(5);
		System.out.println(queue);

		System.out.println("Pop: " + queue.popReverse());
		System.out.println(queue);
		System.out.println("Pop: " + queue.popReverse());
		System.out.println(queue);
		System.out.println("Pop: " + queue.popReverse());
		System.out.println(queue);
		System.out.println("Pop: " + queue.popReverse());
		System.out.println(queue);
		System.out.println("Pop: " + queue.popReverse());
		System.out.println(queue);

	}
}
