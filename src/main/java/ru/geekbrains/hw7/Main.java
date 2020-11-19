package ru.geekbrains.hw7;

public class Main {

	public static void main(String[] args) {
		Cat cat1 = new Cat("Barsik", 11);
		Cat cat2 = new Cat("Willy", 19);
		Cat cat3 = new Cat("Tony", 22);
		Cat cat4 = new Cat("Smoking", 17);
		Cat cat5 = new Cat("Boris", 26);
		Cat cat6 = new Cat("Timmy", 13);

		Cat[] allCats = {cat1, cat2, cat3, cat4, cat5, cat6};

		Plate plate = new Plate(100);
		plate.info();

		System.out.println("Let's go to eat");

		dinner(allCats, plate);

		plate.info();

		boolean someCatsHungry = areSomeCatsHungry(allCats);

		if (someCatsHungry) {

			System.out.println("There are hungry cats. Increasing food...");

			plate.increaseFood(100);

			plate.info();

			dinner(allCats, plate);

			plate.info();
		}

		someCatsHungry = areSomeCatsHungry(allCats);

		if (someCatsHungry) {
			System.out.println("No food, need to buy a new one");
		} else {
			System.out.println("Everybody is happy!");
		}
	}

	private static void dinner(Cat[] cats, Plate plate) {
		for (Cat c : cats) {
			if (!c.isSatiety()) {
				c.eat(plate);
				System.out.println(c);
			}
		}
	}

	private static boolean areSomeCatsHungry(Cat[] cats) {
		int hungryCount = 0;
		for (Cat c : cats) {
			if (!c.isSatiety()) {
				hungryCount++;
			}
		}
		return hungryCount != 0;
	}

}
