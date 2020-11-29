package ru.geekbrains.course2.hw1.obstacles;


import ru.geekbrains.course2.hw1.participants.Participant;

public class Wall extends Obstacle {
    private final int height;

    public Wall(int height) {
        this.height = height;
    }

    @Override
    public void doIt(Participant p) {
        p.jump(height);
    }
}
