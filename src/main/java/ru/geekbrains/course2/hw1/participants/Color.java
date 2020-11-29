package ru.geekbrains.course2.hw1.participants;

public enum Color {
    BLACK("Black", "Черный"),
    GREY("Grey", "Серый"),
    WHITE("White", "Белый"),
    RED("Orange", "Рыжий");

    private final String englishColorName;
    private final String russianColorName;

    Color(String englishColorName, String russianColorName) {
        this.englishColorName = englishColorName;
        this.russianColorName = russianColorName;
    }

    public String getEnglishColorName() {
        return englishColorName;
    }

    public String getRussianColorName() {
        return russianColorName;
    }
}
