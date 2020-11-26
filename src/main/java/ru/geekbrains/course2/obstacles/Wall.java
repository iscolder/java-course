package ru.geekbrains.course2.obstacles;


import ru.geekbrains.course2.participants.Participant;

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
