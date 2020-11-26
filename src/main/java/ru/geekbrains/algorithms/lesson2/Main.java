package ru.geekbrains.algorithms.lesson2;

import java.util.Arrays;

public class Main {


	public static void main(String[] args) {


		int[] arr = new int[10];
		int[] arr1 = {3, 5, 6, 7};
		int[] arr2 = new int[]{3, 5, 6, 7};

		for (int i  = 0 ; i < arr.length; i++) {
			arr[i] = i;
		}

		System.out.println(Arrays.toString(arr));


		for (int i  = 0 ; i < arr.length; i++) {
			if (arr[i] % 3 == 0) {
				arr[i] *= 10;
			}
		}

		System.out.println(Arrays.toString(arr));


		String[] def = new String[] {"a", "b", "c"};

		for (String s : def) {
			s = "1";
		}

		System.out.println(Arrays.toString(def));



		MyArrayList<Integer> mal = new MyArrayList<>();
		mal.add(5);
		mal.add(4);
		mal.add(3);
		mal.add(2);
		mal.add(1);

		System.out.println(mal);

		mal.add(2, 99);

		System.out.println(mal);

	}


}
