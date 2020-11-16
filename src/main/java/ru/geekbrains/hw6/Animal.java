package ru.geekbrains.hw6;

public abstract class Animal {

	protected String name;

	protected double runDistance;
	protected double swimDistance;
	protected double jumpHeight;

	public Animal(String name, double runDistance, double swimDistance, double jumpHeight) {
		this.name = name;
		this.runDistance = runDistance;
		this.swimDistance = swimDistance;
		this.jumpHeight = jumpHeight;
	}

	public void run(double distance) {
		System.out.println(name + ": Бег на " + distance + " метров, результат : " + (getMaxDistanceForRun() >= distance && distance > 0));
	}

	public void swim(double distance) {
		System.out.println(name + ": Плавание на " + distance + " метров, результат: " + (getMaxDistanceForSwim() >= distance && distance > 0));
	}

	public void jump(double height) {
		System.out.println(name + ": Прыжок на " + height + " метров, резудьтат: " + (getMaxHeightForJump() >= height && height > 0));
	}

	protected double getMaxDistanceForRun() {
		return runDistance;
	}

	protected double getMaxDistanceForSwim() {
		return swimDistance;
	}

	protected double getMaxHeightForJump() {
		return jumpHeight;
	}

}
