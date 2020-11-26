package ru.geekbrains.algorithms.lesson2;

public class MySortedArrayList<T extends Comparable<T>> extends MyArrayList<T> {

	@Override
	public void add(T item) {
		int i = 0;
		while (i < size() && get(i).compareTo(item) < 0) {
			i++;
		}
		super.add(i, item);
	}

	@Override
	public void add(int index, T item) {
		add(item);
	}

	@Override
	public void set(int index, T item) {
		throw new UnsupportedOperationException();
	}

	public int binaryFind(T item) {
		int lo = 0;
		int hi = size() - 1;

		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2; // (lo + hi) / 2 !!! ошибка переполнения для больших значений
			if (item.compareTo(get(mid)) < 0) {
				hi = mid - 1;
			} else if (item.compareTo(get(mid)) > 0) {
				lo = mid + 1;
			} else {
				return mid;
			}
		}

		return -1;
	}
}
