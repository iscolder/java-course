package ru.geekbrains;

import java.util.Arrays;

public class HW2 {
	
	public static void main(String[] args) {
		System.out.println("Task 1: " + Arrays.toString(task1(new int[]{1, 1, 0, 0, 1, 0, 1, 1, 0, 0 })));
		System.out.println("Task 1: " + Arrays.toString(task1(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0 })));
		System.out.println("Task 1: " + Arrays.toString(task1(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1 })));

		System.out.println("Task 2: " + Arrays.toString(task2()));

		System.out.println("Task 3: " + Arrays.toString(task3(new int[]{1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1})));

		task4(new int[][]{ {0, 0, 0}, {0, 0, 0}, {0, 0, 0} });
		task4(new int[][]{ {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0} });
		task4(new int[][]{ {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0} });

		task5(new int[]{1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1});

		System.out.println("Task 6: " + task6(new int[]{2, 2, 2, 1, 2, 2, 10, 1}));
		System.out.println("Task 6: " + task6(new int[]{1, 1, 1, 2, 1}));
		System.out.println("Task 6: " + task6(new int[]{1, 2, 1, 2, 1}));
		System.out.println("Task 6: " + task6(new int[]{1, 1}));

		System.out.println("Task 7: " + Arrays.toString(task7(new int[]{1, 2, 3, 4, 5 ,6}, 1)));
		System.out.println("Task 7: " + Arrays.toString(task7(new int[]{1, 2, 3, 4, 5 ,6}, 2)));
		System.out.println("Task 7: " + Arrays.toString(task7(new int[]{1, 2, 3, 4, 5 ,6}, 3)));
		System.out.println("Task 7: " + Arrays.toString(task7(new int[]{1, 2, 3, 4, 5 ,6}, -1)));
		System.out.println("Task 7: " + Arrays.toString(task7(new int[]{1, 2, 3, 4, 5 ,6}, -2)));
		System.out.println("Task 7: " + Arrays.toString(task7(new int[]{1, 2, 3, 4, 5 ,6}, -3)));
	}


	/**
	 * Задать целочисленный массив, состоящий из элементов 0 и 1. Например: [ 1, 1, 0, 0, 1, 0, 1, 1, 0, 0 ]. С помощью цикла и условия заменить 0 на 1, 1 на 0;
	 *
	 * */
	private static int[] task1(int[] input) {
		for (int i = 0; i < input.length; i++) {
			input[i] = input[i] == 0 ? 1 : 0;
		}
		return input;
	}

	/**
	 * Задать пустой целочисленный массив размером 8. С помощью цикла заполнить его значениями 0 3 6 9 12 15 18 21;
	 *
	 * */
	private static int[] task2() {
		int[] array = new int[8];
		for (int i = 0; i < array.length; i++) {
			array[i] = i*3;
		}
		return array;
	}

	/**
	 * Задать массив [ 1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1 ] пройти по нему циклом, и числа меньшие 6 умножить на 2;
	 *
	 * */
	private static int[] task3(int[] array) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] < 6) {
				array[i] = array[i] * 2;
			}
		}
		return array;
	}

	/**
	 * Создать квадратный двумерный целочисленный массив (количество строк и столбцов одинаковое), и с помощью цикла(-ов) заполнить его диагональные элементы единицами;
	 *
	 * */
	private static void task4(int[][] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (i == j || i + j == array[i].length - 1) {
					array[i][j] = 1;
				}
			}
		}
		System.out.println("Task 4:");
		for (int i = 0; i < array.length; i++) {
			System.out.println(Arrays.toString(array[i]));
		}
	}

	/**
	 * Задать одномерный массив и найти в нем минимальный и максимальный элементы (без помощи интернета);
	 *
	 * */
	private static void task5(int[] array) {
		int max = array[0];
		int min = max;
		for (int i = 0; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
			if (array[i] < min) {
				min = array[i];
			}
		}
		System.out.println("Task 5, Max: " + max);
		System.out.println("Task 5, Min: " + min);
	}

	/**
	 * Написать метод, в который передается не пустой одномерный целочисленный массив, метод должен вернуть true,
	 * если в массиве есть место, в котором сумма левой и правой части массива равны.
	 * Примеры: checkBalance([2, 2, 2, 1, 2, 2, || 10, 1]) → true, checkBalance([1, 1, 1, || 2, 1]) → true,
	 * граница показана символами ||, эти символы в массив не входят.
	 *
	 * */
	private static boolean task6(int[] array) {
		for (int i = 0; i < array.length; i++) {
			int sum1 = 0;
			for (int j = 0; j <=i; j++) {
				sum1 += array[j];
			}
			int sum2 = 0;
			for (int j = i + 1; j < array.length; j++) {
				sum2 += array[j];
			}

			if (sum1 == sum2) return true;
		}

		return false;
	}

	/**
	 * Написать метод, которому на вход подается одномерный массив и число n (может быть положительным, или отрицательным),
	 * при этом метод должен сместить все элементы массива на n позиций. Для усложнения задачи нельзя пользоваться вспомогательными массивами.
	 *
	 * */
	private static int[] task7(int[] array, int n) {
		int length = array.length;

		if (Math.abs(n) > length) {
			n = n % length;
		}

		if (n < 0) {
			n = length - Math.abs(n);
		}

		while (n > 0) {
			int temp = array[length - 1];

			for (int i = length - 1; i > 0; i--) {
				array[i] = array[i - 1];
			}

			array[0] = temp;
			n--;
		}

		return array;
	}

}
