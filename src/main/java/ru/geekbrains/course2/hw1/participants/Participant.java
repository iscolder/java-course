package ru.geekbrains.course2.hw1.participants;

public abstract class Participant {

	private final int maxRunDistance;
	private final int maxJumpHeight;
	private final int maxSwimDistance;

	private boolean onDistance;
	private boolean isStarted;

	public Participant(int maxRunDistance, int maxJumpHeight, int maxSwimDistance) {
		this.maxRunDistance = maxRunDistance;
		this.maxJumpHeight = maxJumpHeight;
		this.maxSwimDistance = maxSwimDistance;
		this.onDistance = true;
	}

	public void run(int distance) {
		exercise(distance, "Run", maxRunDistance);
	}

	public void jump(int height) {
		exercise(height, "Jump", maxJumpHeight);
	}

	public void swim(int distance) {
		exercise(distance, "Swim", maxSwimDistance);
	}

	private void exercise(int result, String name, int standard) {
		isStarted = true;
		if (result <= standard) {
			System.out.println(getDescription() + " - " + name + " OK");
		} else {
			System.out.println(getDescription() + " - " + name + " FAILED");
			onDistance = false;
		}
	}

	public boolean isOnDistance() {
		return onDistance;
	}

	public boolean isStarted() {
		return isStarted;
	}

	public void info() {
		System.out.println(getDescription() + ": " + onDistance);
	}

	protected abstract String getDescription();
}
