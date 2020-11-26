package ru.geekbrains.course1;

public class HW1 {
	
	public static void main(String[] args) {

		task1();

		task2();

		System.out.println("Task 3: " + task3(1, 2, 3, 4));
		System.out.println("Task 3: " + task3(5, 6, 7, 8));
		System.out.println("Task 3: " + task3(2, 2, 2, 0));
		System.out.println("Task 3: " + task3(3, 3, 3, 3));

		System.out.println("Task 4: " + task4(4, 1));
		System.out.println("Task 4: " + task4(3, 11));

		task5(4);
		task5(-23);

		System.out.println("Task 6: " + task6(4));
		System.out.println("Task 6: " + task6(-4));

		task7("Kostya");
		task7("Sveta");

		task8(1996);
		task8(1900);
		task8(2000);

	}


	/**
	 * Создать пустой проект в IntelliJ IDEA и прописать метод main();
	 */
	private static void task1() {
		System.out.println("Done");
	}

	/**
	 * Создать переменные всех пройденных типов данных, и инициализировать их значения;
	 */
	private static void task2() {
		byte b = 1;
		short s = 2;
		int i = 3;
		long l = 8L;
		float f = 4f;
		double d = 5d;
		boolean bool = true;
	}

	/**
	 * Написать метод вычисляющий выражение a * (b + (c / d)) и возвращающий результат,где a, b, c, d – входные параметры этого метода;
	 */
	private static float task3(int a, int b, int c, int d) {
		if (d == 0) return 0f;
		return a * (b + (c * 1f / d));
	}

	/**
	 * Написать метод, принимающий на вход два числа, и проверяющий что их сумма лежит в пределах от 10 до 20(включительно), если да – вернуть true, в противном случае – false;
	 */
	private static boolean task4(int x, int y) {
		int s = x + y;
		return s > 10 && s <= 20;
	}

	/**
	 * Написать метод, которому в качестве параметра передается целое число, метод должен напечатать в консоль положительное ли число передали, или отрицательное; Замечание: ноль считаем положительным числом.
	 */
	private static void task5(int a) {
		if (a >= 0) {
			System.out.println(a + " is positive");
		} else {
			System.out.println(a + " is negative");
		}
	}

	/**
	 * Написать метод, которому в качестве параметра передается целое число, метод должен вернуть true, если число отрицательное;
	 */
	private static boolean task6(int a) {
		return a < 0;
	}

	/**
	 * Написать метод, которому в качестве параметра передается строка, обозначающая имя, метод должен вывести в консоль сообщение «Привет, указанное_имя!»;
	 */
	private static void task7(String name) {
		System.out.println("Hello, " + name + "!");
	}

	/**
	 * Написать метод, который определяет является ли год високосным, и выводит сообщение в консоль. Каждый 4-й год является високосным, кроме каждого 100-го, при этом каждый 400-й – високосный.
	 */
	private static void task8(int year) {
		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
			System.out.println(year + " is a leap year");
		} else {
			System.out.println(year + " is NOT a leap year");
		}
	}
}
