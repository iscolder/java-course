package ru.geekbrains.course2.hw1;

import ru.geekbrains.course2.hw1.participants.*;
import ru.geekbrains.course2.hw1.obstacles.*;

public class Main {

	public static void main(String[] args) {
		Participant[] participants = {
				new Cat("Barsik", Color.RED),
				new Dog("Bobik", Color.BLACK),
				new Duck("Scrooge", Color.WHITE),
				new Robot(1000, 1000, 1000, "T-800", "Arni")
		};

		Obstacle[] obstacles = {new Cross(100), new Wall(10), new Water(15)};

		Team team = new Team("Avengers", participants);
		Course course = new Course(obstacles);

		course.doIt(team);

		team.showResults();

		team.showSucceededResults();
	}
}
