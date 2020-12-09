package ru.geekbrains.course2.hw5;

import java.util.Arrays;

public class Main {

	private static final int size = 10000000;
	private static final int h = size / 2;
	private static final float[] arr = new float[size];

	public static void main(String[] args) {
		init();
		measureTime("Sequential", Main::execSeqEvaluation);
		//print();
		init();
		measureTime("Concurrent", Main::execConcurEvaluation);
		//print();
	}

	private static void init() {
		for (int i = 0; i < size; i++) {
			arr[i] = 1;
		}
	}

	private static void measureTime(String method, Exec exec) {
		long start = System.nanoTime();
		exec.execute();
		long end = System.nanoTime();
		System.out.println(method + ", time spent: " + (end - start) / 1000000 + " ms");
	}

	private static void execSeqEvaluation() {
		eval(arr, 0, size);
	}

	/**
	 * @param array - входной массив
	 * @param startPosition - начальная позиция
	 * @param endPosition - конечная позиция
	 *
	 * При последовательном и параллельном вычислении значения в массиве должны быть одинаковыми
	 *
	 * */
	private static void eval(float[] array, int startPosition, int endPosition) {
		for (int i = startPosition; i < endPosition; i++) {
			array[i] = (float) (array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
		}
	}

	private static void execConcurEvaluation() {
		float[] a1 = new float[size];
		float[] a2 = new float[size];

		System.arraycopy(arr, 0, a1, 0, h);
		System.arraycopy(arr, h, a2, h, h);

		Thread t1 = new Thread(() -> eval(a1, 0, h));
		Thread t2 = new Thread(() -> eval(a2, h, size));

		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (Exception e) {
			System.out.println("Evaluation failed: " + e.getMessage());
			return;
		}

		System.arraycopy(a1, 0, arr, 0, h);
		System.arraycopy(a2, h, arr, h, h);
	}

	@FunctionalInterface
	interface Exec {
		void execute();
	}

	private static void print() {
		System.out.println(Arrays.toString(arr));
	}


}
