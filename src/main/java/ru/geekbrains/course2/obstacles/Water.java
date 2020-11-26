package ru.geekbrains.course2.obstacles;


import ru.geekbrains.course2.participants.Participant;

public class Water extends Obstacle {
    private final int length;

    public Water(int length) {
        this.length = length;
    }

    @Override
    public void doIt(Participant p) {
        p.swim(length);
    }
}
