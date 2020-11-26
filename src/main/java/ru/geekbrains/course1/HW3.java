package ru.geekbrains.course1;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class HW3 {


	public static void main(String[] args) {
		guessNumber();
		//guessWord();
	}

	/**
	 * Написать программу, которая загадывает случайное число от 0 до 9, и пользователю дается 3 попытки угадать это число.
	 * При каждой попытке компьютер должен сообщить больше ли указанное пользователем число чем загаданное, или меньше.
	 * После победы или проигрыша выводится запрос – «Повторить игру еще раз? 1 – да / 0 – нет»(1 – повторить, 0 – нет).
	 */
	private static void guessNumber() {

		Scanner inputReader = new Scanner(System.in);

		boolean continueToPlay = true;

		while (continueToPlay) {

			int numberOfGuesses = 3;
			int randomNumberToGuess = new Random().nextInt(10);

			System.out.println("Guess a number from 0 to 9");
			System.out.println("You have " + numberOfGuesses + " attempt(s)");

			while (numberOfGuesses > 0) {

				System.out.print("Input a number: ");

				while (!inputReader.hasNextInt()) {
					System.out.println("That's not a number!");
					inputReader.next();
				}

				int userNumber = inputReader.nextInt();

				if (userNumber == randomNumberToGuess) {
					break;
				} else if (userNumber > randomNumberToGuess) {
					System.out.print("The correct number is less than your. " + --numberOfGuesses + " attempt(s) left!");
				} else {
					System.out.print("The correct number is more than your. " + --numberOfGuesses + " attempt(s) left!");
				}

				System.out.println();
			}

			if (numberOfGuesses == 0) {
				System.out.println("You lost! ");
			} else {
				System.out.print("You won! ");
			}

			System.out.println("Do you want to play again? 1 - yes, 0 - no: ");

			while (!inputReader.hasNextInt()) {
				System.out.println("Do you want to play again? 1 - yes, 0 - no: ");
				inputReader.next();
			}

			continueToPlay = inputReader.nextInt() == 1;
		}

		System.out.println("Game over");
	}

	/**
	 * Создать массив из слов String[] words = {"apple", "orange", "lemon", "banana", "apricot", "avocado", "broccoli", "carrot", "cherry", "garlic", "grape", "melon", "leak", "kiwi", "mango", "mushroom", "nut", "olive", "pea", "peanut", "pear", "pepper", "pineapple", "pumpkin", "potato"};
	 * <p>
	 * При запуске программы компьютер загадывает слово, запрашивает ответ у пользователя,
	 * сравнивает его с загаданным словом и сообщает правильно ли ответил пользователь. Если слово не угадано, компьютер показывает буквы которые стоят на своих местах.
	 * apple – загаданное
	 * apricot - ответ игрока
	 * ap############# (15 символов, чтобы пользователь не мог узнать длину слова)
	 * Для сравнения двух слов посимвольно, можно пользоваться:
	 * String str = "apple";
	 * str.charAt(0); - метод, вернет char, который стоит в слове str на первой позиции
	 * Играем до тех пор, пока игрок не отгадает слово
	 * Используем только маленькие буквы
	 */
	private static void guessWord() {

		String[] words = {"apple", "orange", "lemon", "banana", "apricot", "avocado", "broccoli", "carrot", "cherry", "garlic", "grape", "melon", "leak", "kiwi", "mango", "mushroom", "nut", "olive", "pea", "peanut", "pear", "pepper", "pineapple", "pumpkin", "potato"};

		int randomIndexOfWordToGuess = new Random().nextInt(words.length);
		String wordToGuess = words[randomIndexOfWordToGuess];

		boolean continueToPlay = true;

		Scanner inputReader = new Scanner(System.in);

		System.out.println("Try to guess a word from " + Arrays.toString(words));

		System.out.print("Input a word: ");

		while (continueToPlay) {
			String userWord = inputReader.nextLine();
			if (wordsEqual(userWord, wordToGuess)) {
				System.out.println("You Won!");
				continueToPlay = false;
			} else {
				System.out.println("You guessed the following characters: " + Arrays.toString(findMatchedCharacters(userWord, wordToGuess)));
				System.out.print("Try again. Input a word: ");
			}
		}

	}

	private static boolean wordsEqual(String userWord, String wordToGuess) {
		if (userWord.length() != wordToGuess.length()) return false;
		for (int i = 0; i < userWord.length(); i++) {
			char userChar = userWord.charAt(i);
			char wordToGuessChar = wordToGuess.charAt(i);
			if (userChar != wordToGuessChar) return false;
		}
		return true;
	}

	private static char[] findMatchedCharacters(String userWord, String wordToGuess) {
		char[] matchingCharacters = new char[15];

		int wordToGuessLength = wordToGuess.length();
		int userWordLength = userWord.length();

		for (int i = 0; i < wordToGuessLength; i++) {
			char wordToGuessCharacter = wordToGuess.charAt(i);

			if (i == userWordLength) break;

			char userCharacter = userWord.charAt(i);

			if (wordToGuessCharacter == userCharacter) {
				matchingCharacters[i] = userCharacter;
			} else {
				matchingCharacters[i] = '#';
			}
		}

		int lesserLength = Math.min(userWordLength, wordToGuessLength);

		if (lesserLength <= 15) {
			for (int i = lesserLength; i < 15; i++) {
				matchingCharacters[i] = '#';
			}
		}

		return matchingCharacters;
	}

}
