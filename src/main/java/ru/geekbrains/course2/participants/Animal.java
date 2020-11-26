package ru.geekbrains.course2.participants;

public abstract class Animal extends Participant {
    private final String type;
    private final String name;
    private final Color color;

    public Animal(String type, String name, Color color, int maxRunDistance, int maxJumpHeight, int maxSwimDistance) {
        super(maxRunDistance, maxJumpHeight, maxSwimDistance);
        this.type = type;
        this.name = name;
        this.color = color;
    }

    @Override
    protected String getDescription() {
        return color.getEnglishColorName() + " " + type + " " + name;
    }

}
