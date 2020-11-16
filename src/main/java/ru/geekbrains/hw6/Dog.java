package ru.geekbrains.hw6;

public class Dog extends Animal {

	public Dog() {
		this("Шарик", 500, 10, 0.5);
	}

	public Dog(String name, double runDistance, double swimDistance, double jumpHeight) {
		super(name, runDistance, swimDistance, jumpHeight);
	}
}
