package ru.geekbrains.course1.hw7;

public class Cat {

	private final String name;
	private final int appetite;
	private boolean satiety;

	public Cat(String name, int appetite) {
		this.name = name;
		this.appetite = appetite;
		this.satiety = false;
	}

	public void eat(Plate p) {
		if (appetite < p.getFood()) {
			p.decreaseFood(appetite);
			satiety = true;
		}
	}

	public boolean isSatiety() {
		return satiety;
	}

	@Override
	public String toString() {
		return "Cat{" +
				"name='" + name + '\'' +
				", appetite=" + appetite +
				", satiety=" + satiety +
				'}';
	}
}
