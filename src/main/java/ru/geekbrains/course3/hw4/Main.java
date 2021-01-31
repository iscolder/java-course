package ru.geekbrains.course3.hw4;

import java.util.ArrayList;
import java.util.List;

public class Main {

	private static volatile String CURRENT_LETTER = "A";


	public static void main(String[] args) {
		List<String> result = new ArrayList<>();

		Resource resource = new Resource(result);

		Thread a = new Thread(new LetterProducer("A", "B", resource));
		Thread b = new Thread(new LetterProducer("B", "C", resource));
		Thread c = new Thread(new LetterProducer("C", "A", resource));

		a.start();
		b.start();
		c.start();

		try {
			a.join();
			b.join();
			c.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Result: " + result);
	}

	static class LetterProducer implements Runnable {

		private final String letter;
		private final String nextLetter;
		private final Resource resource;

		LetterProducer(String letter, String nextLetter, Resource resource) {
			this.letter = letter;
			this.nextLetter = nextLetter;
			this.resource = resource;
		}

		@Override
		public void run() {
			for (int i = 0; i < 5; i++) {
				resource.populateResult(letter, nextLetter);
			}
			resource.release();
		}
	}

	static final class Resource {

		private final List<String> result;

		Resource(List<String> result) {
			this.result = result;
		}

		void populateResult(String letter, String nextLetter) {
			synchronized (this) {
				if (!CURRENT_LETTER.equals(letter)) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				result.add(letter);
				CURRENT_LETTER = nextLetter;
				notifyAll();
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		void release() {
			synchronized (this) {
				notifyAll();
			}
		}

	}
}
