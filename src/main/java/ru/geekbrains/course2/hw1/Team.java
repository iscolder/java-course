package ru.geekbrains.course2.hw1;

import ru.geekbrains.course2.hw1.participants.*;

public class Team {
	private final String name;
	private final Participant[] participants;

	public Team(String name, Participant[] participants) {
		this.name = name;
		if (participants == null) {
			participants = new Participant[4];
		}
		this.participants = participants;
	}

	public void showResults() {
		System.out.println();
		System.out.println("Results of " + name + ": ");
		for (Participant p : participants) {
			p.info();
		}
	}

	public void showSucceededResults() {
		System.out.println();
		System.out.println("Winners of " + name + ": ");
		for (Participant p : participants) {
			if (p.isStarted() && p.isOnDistance()) {
				p.info();
			}
		}
	}

	public Participant[] getParticipants() {
		return participants;
	}
}
