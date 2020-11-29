package ru.geekbrains.course2.hw1.participants;

public class Robot extends Participant {
	private final String serialNumber;
	private final String name;

	public Robot(int maxRunDistance, int maxJumpHeight, int maxSwimDistance, String serialNumber, String name) {
		super(maxRunDistance, maxJumpHeight, maxSwimDistance);
		this.serialNumber = serialNumber;
		this.name = name;
	}

	@Override
	protected String getDescription() {
		return "Robot: " + name + " " + serialNumber;
	}
}
