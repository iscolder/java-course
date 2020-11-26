package ru.geekbrains.course2;

import ru.geekbrains.course2.obstacles.Obstacle;
import ru.geekbrains.course2.participants.Participant;

public class Course {
	private final Obstacle[] obstacles;

	public Course(Obstacle[] obstacles) {
		if (obstacles == null) {
			obstacles = new Obstacle[3];
		}
		this.obstacles = obstacles;
	}

	public void doIt(Team team) {
		for (Obstacle o : obstacles) {
			for (Participant p : team.getParticipants()) {
				o.doIt(p);
			}
		}
	}
}
