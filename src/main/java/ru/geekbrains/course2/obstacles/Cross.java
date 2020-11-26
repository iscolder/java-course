package ru.geekbrains.course2.obstacles;

import ru.geekbrains.course2.participants.Participant;

public class Cross extends Obstacle {
    private final int length;

    public Cross(int length) {
        this.length = length;
    }

    @Override
    public void doIt(Participant p) {
        p.run(length);
    }
}
