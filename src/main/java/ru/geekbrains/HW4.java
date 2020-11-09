package ru.geekbrains;

import java.util.Random;
import java.util.Scanner;

public class HW4 {

	// DISABLE IT IF NEEDED
	private static boolean aiBrainEnabled = true;

	public static final char DOT_EMPTY = '•';
	public static final char DOT_X = 'X';
	public static final char DOT_O = 'O';
	public static char[][] map;
	public static Scanner sc = new Scanner(System.in);
	public static Random rand = new Random();

	private static int maxNumberOfUserDots = 0; // максимальное количество фишек пользователя по горизонтали или по вертикали или диагонали
	private static int maxNumberOfAiDots = 0; // максимальное количество фишек AI по горизонтали или по вертикали или диагонали

	private static int aiTurnRow = -1;
	private static int aiTurnCol = -1;

	public static int dotsToWin = 0;

	public static void main(String[] args) {
		int mapSize = setupMapSize();
		dotsToWin = setupNumberOfDotsToWin(mapSize);

		initMap(mapSize);
		printMap();

		while (true) {
			humanTurn();
			printMap();
			if (isWon(DOT_X)) {
				System.out.println("Победил человек");
				break;
			}
			if (isMapFull()) {
				System.out.println("Ничья");
				break;
			}
			aiTurn();
			printMap();
			if (isWon(DOT_O)) {
				System.out.println("Победил Искуственный Интеллект");
				break;
			}
			if (isMapFull()) {
				System.out.println("Ничья");
				break;
			}
		}
		System.out.println("Игра закончена");
	}

	private static boolean aiPlanTurn() {
		aiTurnRow = -1;
		aiTurnCol = -1;
		maxNumberOfAiDots = 0;

		aiPlanTurnOnRow();
		aiPlanTurnOnColumn();
		aiPlanTurnOnMainDiagonal();
		aiPlanTurnOnOppositeDiagonal();
		return maxNumberOfAiDots + 1 == dotsToWin;
	}

	private static boolean aiBlockUserTurn() {
		// сбрасываем переменные
		maxNumberOfUserDots = 0;
		aiTurnRow = -1;
		aiTurnCol = -1;

		aiBlocksUserOnRow();
		aiBlocksUserOnColumn();
		aiBlocksUserOnMainDiagonal();
		aiBlocksUserOnOppositeDiagonal();

		return maxNumberOfUserDots + 1 == dotsToWin;
	}

	private static void aiBlocksUserOnRow() {
		// проверяем rows
		for (int i = 0; i < map.length; i++) {
			int currentNumberOfUserDots = 0; // текущее количество фишек полязователя в ряду
			int currentNumberOfSymbols = 0; // общее количесво символов в ряду (DOT_X+ DOT_EMPTY)
			int[] bestTurnHolder = new int[2];

			boolean hasPriority = false;

			for (int j = 0; j < map.length; j++) {
				if (map[i][j] == DOT_X) { // нашли фишку пользователя
					currentNumberOfUserDots++;
					currentNumberOfSymbols++;
				} else if (map[i][j] == DOT_EMPTY) { // нашли пустую фишку
					if (j > 0 && map[i][j - 1] == DOT_X && j < map.length - 1 && map[i][j + 1] == DOT_X) { // пытаемся быть ближе к пользовательской фишке
						hasPriority = true;
						bestTurnHolder[0] = i;
						bestTurnHolder[1] = j;
					} else if (((j > 0 && map[i][j - 1] == DOT_X) || (j < map.length - 1 && map[i][j + 1] == DOT_X)) && !hasPriority) { // пытаемся быть ближе к пользовательской фишке
						bestTurnHolder[0] = i;
						bestTurnHolder[1] = j;
					}
					currentNumberOfSymbols++;
				} else { // нашли  фишку ai
					currentNumberOfUserDots = 0;
					if (map.length - currentNumberOfSymbols <= dotsToWin)
						break; // пользователь не сможет победить в этом ряду, переходим на следующую итерацию
					currentNumberOfSymbols = 0;
				}
			}

			if (currentNumberOfUserDots >= maxNumberOfUserDots) {
				maxNumberOfUserDots = currentNumberOfUserDots;
				aiTurnRow = bestTurnHolder[0];
				aiTurnCol = bestTurnHolder[1];
			}
		}
	}

	private static void aiBlocksUserOnColumn() {
		char[][] rotatedMap = rotateMap();
		for (int i = 0; i < rotatedMap.length; i++) {

			int currentNumberOfUserDots = 0;
			int currentNumberOfSymbols = 0;
			int[] bestTurnHolder = new int[2];

			boolean hasPriority = false;

			for (int j = 0; j < rotatedMap.length; j++) {
				if (rotatedMap[i][j] == DOT_X) {
					currentNumberOfUserDots++;
					currentNumberOfSymbols++;
				} else if (rotatedMap[i][j] == DOT_EMPTY) {
					if (j > 0 && rotatedMap[i][j - 1] == DOT_X && j < rotatedMap.length - 1 && rotatedMap[i][j + 1] == DOT_X) { // пытаемся быть ближе к пользовательской фишке
						hasPriority = true;
						bestTurnHolder[0] = j; // здесь нужно использовать индексы до rotation
						bestTurnHolder[1] = rotatedMap.length - 1 - i; // здесь нужно использовать индексы до rotation
					} else if (((j > 0 && rotatedMap[i][j - 1] == DOT_X) || (j < rotatedMap.length - 1 && rotatedMap[i][j + 1] == DOT_X)) && !hasPriority) {
						bestTurnHolder[0] = j; // здесь нужно использовать индексы до rotation
						bestTurnHolder[1] = rotatedMap.length - 1 - i; // здесь нужно использовать индексы до rotation
					}
					currentNumberOfSymbols++;
				} else {
					currentNumberOfUserDots = 0;
					if (rotatedMap.length - currentNumberOfSymbols <= dotsToWin) {
						break;
					}
					currentNumberOfSymbols = 0;
				}
			}

			if (currentNumberOfUserDots >= maxNumberOfUserDots) {
				maxNumberOfUserDots = currentNumberOfUserDots;
				aiTurnRow = bestTurnHolder[0];
				aiTurnCol = bestTurnHolder[1];
			}
		}
	}


	private static void aiBlocksUserOnMainDiagonal() {
		int counter = dotsToWin - map.length;
		do {

			int currentNumberOfUserDots = 0;
			int numberOfSymbols = 0;

			int[] bestTurnHolder = new int[2];

			boolean hasPriority = false;

			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map.length; j++) {
					if (j - i == counter) {
						if (map[i][j] == DOT_X) {
							currentNumberOfUserDots++;
							numberOfSymbols++;
						} else if (map[i][j] == DOT_EMPTY) {
							if (((j > 0 && i > 0 && map[i - 1][counter + i - 1] == DOT_X) && (j < map.length - 1 && i < map.length - 1 && map[i + 1][counter + i + 1] == DOT_X))) {
								hasPriority = true;
								bestTurnHolder[0] = i;
								bestTurnHolder[1] = j;
							} else if (((j > 0 && i > 0 && map[i - 1][counter + i - 1] == DOT_X) || (j < map.length - 1 && i < map.length - 1 && map[i + 1][counter + i + 1] == DOT_X)) && !hasPriority) {
								bestTurnHolder[0] = i;
								bestTurnHolder[1] = j;
							}
							numberOfSymbols++;
						} else {
							currentNumberOfUserDots = 0;
							if (map.length - numberOfSymbols <= dotsToWin) break;
							numberOfSymbols = 0;
						}
					}
				}

				if (currentNumberOfUserDots >= maxNumberOfUserDots) {
					maxNumberOfUserDots = currentNumberOfUserDots;
					aiTurnRow = bestTurnHolder[0];
					aiTurnCol = bestTurnHolder[1];
				}

			}
			counter++;
		} while (counter <= map.length - dotsToWin);
	}

	private static void aiBlocksUserOnOppositeDiagonal() {
		char[][] rotatedMap = rotateMap();
		int counter = dotsToWin - map.length;
		do {
			int currentNumberOfUserDots = 0;
			int[] bestTurnHolder = new int[2];
			int numberOfSymbols = 0;
			boolean hasPriority = false;

			for (int i = 0; i < rotatedMap.length; i++) {

				for (int j = 0; j < rotatedMap.length; j++) {
					if (j - i == counter) {
						if (rotatedMap[i][j] == DOT_X) {
							currentNumberOfUserDots++;
							numberOfSymbols++;
						} else if (rotatedMap[i][j] == DOT_EMPTY) {
							if (((j > 0 && i > 0 && rotatedMap[i - 1][counter + i - 1] == DOT_X) && (j < rotatedMap.length - 1 && i < rotatedMap.length - 1 && rotatedMap[i + 1][counter + i + 1] == DOT_X))) {
								hasPriority = true;
								bestTurnHolder[0] = j;
								bestTurnHolder[1] = rotatedMap.length - 1 - i;
							} else if (((j > 0 && i > 0 && rotatedMap[i - 1][counter + i - 1] == DOT_X) || (j < rotatedMap.length - 1 && i < rotatedMap.length - 1 && rotatedMap[i + 1][counter + i + 1] == DOT_X)) && !hasPriority) {
								bestTurnHolder[0] = j;
								bestTurnHolder[1] = rotatedMap.length - 1 - i;
							}
							numberOfSymbols++;
						} else {
							currentNumberOfUserDots = 0;
							if (rotatedMap.length - numberOfSymbols <= dotsToWin) break;
							numberOfSymbols = 0;
						}
					}
				}

				if (currentNumberOfUserDots >= maxNumberOfUserDots) {
					maxNumberOfUserDots = currentNumberOfUserDots;
					aiTurnRow = bestTurnHolder[0];
					aiTurnCol = bestTurnHolder[1];
				}

			}
			counter++;
		} while (counter <= rotatedMap.length - dotsToWin);
	}

	private static void aiPlanTurnOnRow() {
		for (int i = 0; i < map.length; i++) {
			int currentNumberOfAiDots = 0;
			int currentNumberOfSymbols = 0;
			int[] bestTurnHolder = new int[2];

			for (int j = 0; j < map.length; j++) {
				if (map[i][j] == DOT_O) {
					currentNumberOfAiDots++;
					currentNumberOfSymbols++;
				} else if (map[i][j] == DOT_EMPTY) {
					bestTurnHolder[0] = i;
					bestTurnHolder[1] = j;
					currentNumberOfSymbols++;
				} else {
					currentNumberOfAiDots = 0;
					if (map.length - currentNumberOfSymbols <= dotsToWin) break;
					currentNumberOfSymbols = 0;
				}
			}

			if (currentNumberOfAiDots >= maxNumberOfAiDots) {
				maxNumberOfAiDots = currentNumberOfAiDots;
				aiTurnRow = bestTurnHolder[0];
				aiTurnCol = bestTurnHolder[1];
			}
		}
	}

	private static void aiPlanTurnOnColumn() {
		char[][] rotatedMap = rotateMap();

		for (int i = 0; i < rotatedMap.length; i++) {
			int currentNumberOfAiDots = 0;
			int[] bestTurnHolder = new int[2];
			int currentNumberOfSymbols = 0;

			for (int j = 0; j < rotatedMap.length; j++) {
				if (rotatedMap[i][j] == DOT_O) {
					currentNumberOfAiDots++;
					currentNumberOfSymbols++;
				} else if (rotatedMap[i][j] == DOT_EMPTY) {
					bestTurnHolder[0] = j;
					bestTurnHolder[1] = rotatedMap.length - 1 - i;
					currentNumberOfSymbols++;
				} else {
					currentNumberOfAiDots = 0;
					if (rotatedMap.length - currentNumberOfSymbols <= dotsToWin) break;
					currentNumberOfSymbols = 0;
				}
			}

			if (currentNumberOfAiDots >= maxNumberOfAiDots) {
				maxNumberOfAiDots = currentNumberOfAiDots;
				aiTurnRow = bestTurnHolder[0];
				aiTurnCol = bestTurnHolder[1];
			}
		}
	}

	private static void aiPlanTurnOnMainDiagonal() {
		int counter = dotsToWin - map.length;
		do {
			int currentNumberOfAiDots = 0;
			int[] bestTurnHolder = new int[2];
			int numberOfSymbols = 0;

			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map.length; j++) {
					if (j - i == counter) {
						if (map[i][j] == DOT_O) {
							currentNumberOfAiDots++;
							numberOfSymbols++;
						} else if (map[i][j] == DOT_EMPTY) {
							bestTurnHolder[0] = i;
							bestTurnHolder[1] = j;
							numberOfSymbols++;
						} else {
							currentNumberOfAiDots = 0;
							if (map.length - numberOfSymbols <= dotsToWin) break;
							numberOfSymbols = 0;
						}
					}
				}

				if (currentNumberOfAiDots >= maxNumberOfAiDots) {
					maxNumberOfAiDots = currentNumberOfAiDots;
					aiTurnRow = bestTurnHolder[0];
					aiTurnCol = bestTurnHolder[1];
				}
			}
			counter++;
		} while (counter <= map.length - dotsToWin);
	}

	private static void aiPlanTurnOnOppositeDiagonal() {
		char[][] rotatedMap = rotateMap();
		int counter = dotsToWin - rotatedMap.length;

		do {
			int currentNumberOfAiDots = 0;
			int[] bestTurnHolder = new int[2];
			int numberOfSymbols = 0;

			for (int i = 0; i < rotatedMap.length; i++) {
				for (int j = 0; j < rotatedMap.length; j++) {
					if (j - i == counter) {
						if (rotatedMap[i][j] == DOT_O) {
							currentNumberOfAiDots++;
							numberOfSymbols++;
						} else if (rotatedMap[i][j] == DOT_EMPTY) {
							bestTurnHolder[0] = j;
							bestTurnHolder[1] = rotatedMap.length - 1 - i;
							numberOfSymbols++;
						} else {
							currentNumberOfAiDots = 0;
							if (rotatedMap.length - numberOfSymbols <= dotsToWin) break;
							numberOfSymbols = 0;
						}
					}
				}

				if (currentNumberOfAiDots >= maxNumberOfAiDots) {
					maxNumberOfAiDots = currentNumberOfAiDots;
					aiTurnRow = bestTurnHolder[0];
					aiTurnCol = bestTurnHolder[1];
				}
			}
			counter++;
		} while (counter <= rotatedMap.length - dotsToWin);
	}


	// WIN section

	public static boolean isWon(char symbol) {
		return checkWinByRow(symbol) || checkWinByDiagonal(symbol);
	}

	private static boolean checkWinByDiagonal(char symbol) {
		if (checkDiagonal(symbol, map)) return true;
		// переворачиваем "матрицу" против часовой стрелки, чтобы побочная диагональ стала основной и чтобы использовать логику проверки, применяемой для основной диагонали
		char[][] rotatedMap = rotateMap();
		return checkDiagonal(symbol, rotatedMap);
	}

	private static boolean checkDiagonal(char symbol, char[][] currentMap) {
		int counter = dotsToWin - currentMap.length;
		do {
			char[] temp = new char[currentMap.length];
			int d = 0;
			for (int i = 0; i < currentMap.length; i++) {
				for (int j = 0; j < currentMap.length; j++) {
					if (j - i == counter) {
						temp[d++] = currentMap[i][j];
					}
				}
			}
			if (checkWin(temp, symbol)) return true;
			counter++;
		} while (counter <= currentMap.length - dotsToWin);
		return false;
	}

	private static boolean checkWinByRow(char symbol) {
		if (checkRow(symbol, map)) return true;
		// переворачиваем "матрицу" против часовой стрелки, чтобы столбцы стали рядами и можно было применить ту же логику проверки, что и для рядов
		char[][] rotatedMap = rotateMap();
		return checkRow(symbol, rotatedMap);
	}

	private static boolean checkRow(char symbol, char[][] currentMap) {
		for (int i = 0; i < currentMap.length; i++) {
			char[] row = new char[currentMap.length];
			for (int j = 0; j < currentMap.length; j++) {
				row[j] = currentMap[i][j];
			}
			if (checkWin(row, symbol)) return true;
		}
		return false;
	}

	private static char[][] rotateMap() {
		char[][] rotatedMap = new char[map.length][map.length];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				rotatedMap[map.length - 1 - j][i] = map[i][j];
			}
		}
		return rotatedMap;
	}

	private static boolean checkWin(char[] row, char symbol) {
		int counter = 0;
		for (int i = 0; i < map.length; i++) {
			if (row[i] == symbol) {
				counter++;
				if (counter == dotsToWin) return true;
			} else {
				counter = 0;
			}
		}
		return false;
	}

	public static boolean isMapFull() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				if (map[i][j] == DOT_EMPTY) return false;
			}
		}
		return true;
	}

	public static void aiTurn() {

		int x = -1, y = -1;

		if (aiBrainEnabled) {
			boolean aiCanWin = aiPlanTurn();

			if (aiCanWin && map[aiTurnRow][aiTurnCol] == DOT_EMPTY) {
				map[aiTurnRow][aiTurnCol] = DOT_O;
				System.out.println("Компьютер походил в точку " + (aiTurnCol + 1) + " " + (aiTurnRow + 1));
				return;
			} else {
				x = aiTurnCol;
				y = aiTurnRow;

				// bad workaround !!!
				if (map[x][y] != DOT_EMPTY) {
					x = -1;
				}
			}

			boolean userCanWin = aiBlockUserTurn();

			if (userCanWin && map[aiTurnRow][aiTurnCol] == DOT_EMPTY) {
				map[aiTurnRow][aiTurnCol] = DOT_O;
				System.out.println("Компьютер не хочет дать человеку победить!");
				System.out.println("Компьютер походил в точку " + (aiTurnCol + 1) + " " + (aiTurnRow + 1));
				return;
			}
		}

		if (x == -1) {
			do {
				x = rand.nextInt(map.length);
				y = rand.nextInt(map.length);
			} while (!isCellValid(x, y));
		}

		System.out.println("Компьютер походил в точку " + (x + 1) + " " + (y + 1));
		map[y][x] = DOT_O;
	}

	public static void humanTurn() {
		int x, y;
		do {
			System.out.println("Введите координаты в формате X Y");
			x = sc.nextInt() - 1;
			y = sc.nextInt() - 1;
		} while (!isCellValid(x, y));
		map[y][x] = DOT_X;
	}

	public static boolean isCellValid(int x, int y) {
		if (x < 0 || x >= map.length || y < 0 || y >= map.length) return false;
		return map[y][x] == DOT_EMPTY;
	}

	public static int setupMapSize() {
		int mapSize;
		do {
			System.out.println("Введите размер поля: ");
			while (!sc.hasNextInt()) {
				System.out.println("Введите размер поля: ");
				sc.next();
			}
			mapSize = sc.nextInt();
		} while (!mapSizeIsValid(mapSize));
		return mapSize;
	}

	private static boolean mapSizeIsValid(int mapSize) {
		if (mapSize >= 3) return true;
		System.out.println("Размер поля должен быть >= 3");
		return false;
	}

	public static int setupNumberOfDotsToWin(int mapSize) {
		int dotsToWin;
		do {
			System.out.println("Введите количесво фишек для выигрыша: ");
			while (!sc.hasNextInt()) {
				System.out.println("Введите количесво фишек для выигрыша: ");
				sc.next();
			}
			dotsToWin = sc.nextInt();
		} while (!numberOfDotsToWinIsValid(dotsToWin, mapSize));
		return dotsToWin;
	}

	private static boolean numberOfDotsToWinIsValid(int dotsToWin, int mapSize) {
		if (dotsToWin > 2 && dotsToWin <= mapSize) return true;
		System.out.println("Количество фишек для выигрыша должно быть > 2 и <= " + mapSize);
		return false;
	}

	public static void initMap(int size) {
		map = new char[size][size];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				map[i][j] = DOT_EMPTY;
			}
		}
	}

	public static void printMap() {
		for (int i = 0; i <= map.length; i++) {
			System.out.print(i + " ");
		}
		System.out.println();
		for (int i = 0; i < map.length; i++) {
			System.out.print((i + 1) + " ");
			for (int j = 0; j < map.length; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}


}
