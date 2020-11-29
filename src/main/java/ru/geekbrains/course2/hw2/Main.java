package ru.geekbrains.course2.hw2;

public class Main {

	public static void main(String[] args) {

		String[][] input = {
				{"1", "2", "3", "4"},
				{"9", "6", "3", "0"},
				{"7", "7", "7", "7"},
				{"0", "4", "5", "5"}
		};

		ArrayHandler handler = new ArrayHandler(input);
		int result = 0;
		try {
			handler.check();
			result = handler.sum();
		} catch (MyArraySizeException | MyArrayDataException e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("Calculation Result: " + result);
		}

	}

	private static class ArrayHandler {

		private static final int SIZE = 4;

		private final String[][] array;

		private ArrayHandler(String[][] array) {
			this.array = array;
		}

		void check() throws MyArraySizeException {
			if (array == null) {
				throw new MyArraySizeException("Array is NULL");
			}
			int length = array.length;
			if (length != SIZE) {
				throw new MyArraySizeException("Invalid row size: " + length);
			}
			for (int i = 0; i < SIZE; i++) {
				if (array[i].length != SIZE) {
					throw new MyArraySizeException(String.format("Invalid column size: %s for row %s", array[i].length, i));
				}
			}
		}

		int sum() throws MyArrayDataException {
			int sum = 0;
			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					try {
						int value = Integer.parseInt(array[i][j]);
						sum += value;
					} catch (Exception e) {
						throw new MyArrayDataException(array[i][j], i, j);
					}
				}
			}
			return sum;
		}

	}

	private static class MyArraySizeException extends Exception {

		public MyArraySizeException(String message) {
			super(message);
		}

	}

	private static class MyArrayDataException extends Exception {

		public MyArrayDataException(String value, int i, int j) {
			super(String.format("Cannot convert \"%s\" to Integer at position [%s, %s]", value, i, j));
		}

	}

}
