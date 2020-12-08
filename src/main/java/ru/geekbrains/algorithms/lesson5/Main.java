package ru.geekbrains.algorithms.lesson5;

import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) {
		System.out.println(multiply(3, 8));


		String[] words = {"a", "b", "c", "d", "e,f"};

		Stream.of(words).flatMap(s -> Stream.of(s.split(","))).forEach(System.out::println);

	}


	static int factorial(int n) {
		if (n <= 1) {
			return 1;
		}
		return factorial(n - 1) * n;
	}


	static long fibo(int n) {
		long a = 1;
		long b = 1;

		for (int i = 3; i <= n; i++) {
			b = a + b;
			a = b - a;
		}

		return b;
	}


	static long recFibo(int n) {
		if (n <= 2) {
			return 1;
		}
		return recFibo(n - 2) + recFibo(n - 1);
	}

	static int triangle(int n) {
		int sum = 0;
		for (int i = 0; i <= n; i++) {
			sum += i;
		}
		return sum;
	}

	static int recTriangle(int n) {
		if (n == 1) {
			return 1;
		}
		return recTriangle(n - 1) + n;
	}

	static int multiply(int n, int d) {
		if (n == 1) {
			return d;
		}
		return multiply(n - 1, d) + d;
	}

}
