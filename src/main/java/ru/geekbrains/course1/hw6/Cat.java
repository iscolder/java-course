package ru.geekbrains.course1.hw6;

public class Cat extends Animal {

	public Cat() {
		this("Борис", 200, 0, 2);
	}

	public Cat(String name, double runDistance, double swimDistance, double jumpHeight) {
		super(name, runDistance, swimDistance, jumpHeight);
	}

}
