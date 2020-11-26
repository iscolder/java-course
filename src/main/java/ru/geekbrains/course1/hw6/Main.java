package ru.geekbrains.course1.hw6;

public class Main {

	public static void main(String[] args) {

		Animal defaultCat = new Cat();
		Animal superCat = new Cat("Искра", 400, 100, 10);
		Animal noobCat = new Cat("Мелкий", 50, 0, 1);

		Animal defaultDog = new Dog();
		Animal superDog = new Dog("Стингер", 1000, 300, 5);
		Animal noobDog = new Dog("Тузик", 100, 1, 0);

		Animal[] zoo = new Animal[]{defaultCat, superCat, noobCat, defaultDog, superDog, noobDog};
		start(zoo, 50, 50, 5);
		start(zoo, 150, 150, 7);
		start(zoo, 300, 200, 10);
	}

	private static void start(Animal[] zoo, double runDistance, double swimDistance, double jumpHeight) {
		System.out.println("СТАРТ");
		for (Animal animal : zoo) {
			animal.run(runDistance);
			animal.swim(swimDistance);
			animal.jump(jumpHeight);
		}
		System.out.println("ФИНИШ");
		System.out.println();
	}

}
