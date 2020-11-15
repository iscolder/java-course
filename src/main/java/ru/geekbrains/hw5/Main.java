package ru.geekbrains.hw5;

public class Main {

	public static void main(String[] args) {

		Person[] persons = new Person[5]; // Вначале объявляем массив объектов
		persons[0] = new Person("Ivanov Ivan", "Java Developer", "iivanov@mailbox.com", "892312312", 60000, 59);
		persons[1] = new Person("Leonova Anna", "QA Engineer", "aleonova@mailbox.com",  "811111111", 40000, 21);
		persons[2] = new Person("Smirnov Alexander", "Project Manager", "asmirnov@mailbox.com", "822222222", 90000, 45);
		persons[3] = new Person("Sergenko Elena", "Business Analyst", "esergeenko@mailbox.com", "833333333", 50000, 30);
		persons[4] = new Person("Pimenov Andrey", "System Administrator", "apimenov@mailbox.com", "844444444", 30000, 52);

		for (int i = 0; i < persons.length; i++) {
			if (persons[i].getAge() > 40) {
				persons[i].info();
			}
		}
	}

}
