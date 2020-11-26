package ru.geekbrains.course1.hw7;

public class Plate {

	private int food;

	public Plate(int food) {
		this.food = food;
	}

	public void decreaseFood(int n) {
		food -= n;
	}

	public void increaseFood(int n) {
		food += n;
	}

	public void info() {
		System.out.println("Food on the plate: " + food);
	}

	public int getFood() {
		return food;
	}
}
