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

		int[] rowBestTurn = aiPlanTurnOnRow();
		int[] columnBestTurn = aiPlanTurnOnColumn();
		int[] mainDigBestTurn = aiPlanTurnOnMainDiagonal();
		int[] oppDigBestTurn = aiPlanTurnOnOppositeDiagonal();

		int maxDots = 0;
		if (rowBestTurn[2] > maxDots) {
			maxDots = rowBestTurn[2];
			aiTurnRow = rowBestTurn[0];
			aiTurnCol = rowBestTurn[1];
		}
		if (columnBestTurn[2] > maxDots) {
			maxDots = columnBestTurn[2];
			aiTurnRow = columnBestTurn[0];
			aiTurnCol = columnBestTurn[1];
		}
		if (mainDigBestTurn[2] > maxDots) {
			maxDots = mainDigBestTurn[2];
			aiTurnRow = mainDigBestTurn[0];
			aiTurnCol = mainDigBestTurn[1];
		}
		if (oppDigBestTurn[2] > maxDots) {
			maxDots = oppDigBestTurn[2];
			aiTurnRow = oppDigBestTurn[0];
			aiTurnCol = oppDigBestTurn[1];
		}

		return maxDots + 1 == dotsToWin;
	}

	private static boolean aiBlockUserTurn() {
		// сбрасываем переменные
		aiTurnRow = -1;
		aiTurnCol = -1;

		int[] rowBestTurn = aiBlocksUserOnRow();
		int[] columnBestTurn = aiBlocksUserOnColumn();
		int[] mainDigBestTurn = aiBlocksUserOnMainDiagonal();
		int[] oppDigBestTurn = aiBlocksUserOnOppositeDiagonal();

		int maxDots = 0;
		if (rowBestTurn[2] > maxDots) {
			maxDots = rowBestTurn[2];
			aiTurnRow = rowBestTurn[0];
			aiTurnCol = rowBestTurn[1];
		}
		if (columnBestTurn[2] > maxDots) {
			maxDots = columnBestTurn[2];
			aiTurnRow = columnBestTurn[0];
			aiTurnCol = columnBestTurn[1];
		}
		if (mainDigBestTurn[2] > maxDots) {
			maxDots = mainDigBestTurn[2];
			aiTurnRow = mainDigBestTurn[0];
			aiTurnCol = mainDigBestTurn[1];
		}
		if (oppDigBestTurn[2] > maxDots) {
			maxDots = oppDigBestTurn[2];
			aiTurnRow = oppDigBestTurn[0];
			aiTurnCol = oppDigBestTurn[1];
		}

		return maxDots + 1 == dotsToWin;
	}

	private static int[] aiBlocksUserOnRow() {
		int[] bestTurn = new int[3];
		int maxNumberOfUserDots = 0;
		// проверяем rows
		for (int i = 0; i < map.length; i++) {
			int currentNumberOfUserDots = 0; // текущее количество фишек полязователя в ряду
			int currentNumberOfEmptyDots = 0;
			int[] bestTurnHolder = new int[2];

			boolean hasPriority = false;

			for (int j = 0; j < map.length; j++) {
				if (map[i][j] == DOT_X) { // нашли фишку пользователя
					currentNumberOfUserDots++;
				} else if (map[i][j] == DOT_EMPTY) { // нашли пустую фишку
					if (j > 0 && map[i][j - 1] == DOT_X && j < map.length - 1 && map[i][j + 1] == DOT_X) { // пытаемся быть ближе к пользовательской фишке
						hasPriority = true;
						bestTurnHolder[0] = i;
						bestTurnHolder[1] = j;
					} else if (((j > 0 && map[i][j - 1] == DOT_X) || (j < map.length - 1 && map[i][j + 1] == DOT_X)) && !hasPriority) { // пытаемся быть ближе к пользовательской фишке
						bestTurnHolder[0] = i;
						bestTurnHolder[1] = j;
					}
					currentNumberOfEmptyDots++;
				} else { // нашли  фишку ai
					if (map.length - currentNumberOfUserDots - currentNumberOfEmptyDots <= dotsToWin)
						break; // пользователь не сможет победить в этом ряду, переходим на следующую итерацию
					currentNumberOfUserDots = 0;
					currentNumberOfEmptyDots = 0;
				}
			}

			if (currentNumberOfUserDots >= maxNumberOfUserDots) {
				maxNumberOfUserDots = currentNumberOfUserDots;
				bestTurn[0] = bestTurnHolder[0];
				bestTurn[1] = bestTurnHolder[1];
				bestTurn[2] = maxNumberOfUserDots;
			}
		}
		return bestTurn;
	}

	private static int[] aiBlocksUserOnColumn() {

		int[] bestTurn = new int[3];
		int maxNumberOfUserDots = 0;
		char[][] rotatedMap = rotateMap();

		for (int i = 0; i < rotatedMap.length; i++) {

			int currentNumberOfUserDots = 0;
			int currentNumberOfEmptyDots = 0;
			int[] bestTurnHolder = new int[2];

			boolean hasPriority = false;

			for (int j = 0; j < rotatedMap.length; j++) {
				if (rotatedMap[i][j] == DOT_X) {
					currentNumberOfUserDots++;
				} else if (rotatedMap[i][j] == DOT_EMPTY) {
					if (j > 0 && rotatedMap[i][j - 1] == DOT_X && j < rotatedMap.length - 1 && rotatedMap[i][j + 1] == DOT_X) { // пытаемся быть ближе к пользовательской фишке
						hasPriority = true;
						bestTurnHolder[0] = j; // здесь нужно использовать индексы до rotation
						bestTurnHolder[1] = rotatedMap.length - 1 - i; // здесь нужно использовать индексы до rotation
					} else if (((j > 0 && rotatedMap[i][j - 1] == DOT_X) || (j < rotatedMap.length - 1 && rotatedMap[i][j + 1] == DOT_X)) && !hasPriority) {
						bestTurnHolder[0] = j; // здесь нужно использовать индексы до rotation
						bestTurnHolder[1] = rotatedMap.length - 1 - i; // здесь нужно использовать индексы до rotation
					}
					currentNumberOfEmptyDots++;
				} else {
					if (rotatedMap.length - currentNumberOfUserDots - currentNumberOfEmptyDots <= dotsToWin) {
						break;
					}
					currentNumberOfUserDots = 0;
					currentNumberOfEmptyDots = 0;
				}
			}

			if (currentNumberOfUserDots >= maxNumberOfUserDots) {
				maxNumberOfUserDots = currentNumberOfUserDots;
				bestTurn[0] = bestTurnHolder[0];
				bestTurn[1] = bestTurnHolder[1];
				bestTurn[2] = maxNumberOfUserDots;
			}
		}
		return bestTurn;
	}


	private static int[] aiBlocksUserOnMainDiagonal() {
		int[] bestTurn = new int[3];
		int maxNumberOfUserDots = 0;
		int counter = dotsToWin - map.length;
		do {

			int currentNumberOfUserDots = 0;
			int currentNumberOfEmptyDots = 0;

			int[] bestTurnHolder = new int[2];

			boolean hasPriority = false;

			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map.length; j++) {
					if (j - i == counter) {
						if (map[i][j] == DOT_X) {
							currentNumberOfUserDots++;
						} else if (map[i][j] == DOT_EMPTY) {
							if (((j > 0 && i > 0 && map[i - 1][counter + i - 1] == DOT_X) && (j < map.length - 1 && i < map.length - 1 && map[i + 1][counter + i + 1] == DOT_X))) {
								hasPriority = true;
								bestTurnHolder[0] = i;
								bestTurnHolder[1] = j;
							} else if (((j > 0 && i > 0 && map[i - 1][counter + i - 1] == DOT_X) || (j < map.length - 1 && i < map.length - 1 && map[i + 1][counter + i + 1] == DOT_X)) && !hasPriority) {
								bestTurnHolder[0] = i;
								bestTurnHolder[1] = j;
							}
							currentNumberOfEmptyDots++;
						} else {
							if (map.length - currentNumberOfEmptyDots - currentNumberOfUserDots <= dotsToWin) break;
							currentNumberOfEmptyDots = 0;
							currentNumberOfUserDots = 0;
						}
					}
				}

				if (currentNumberOfUserDots >= maxNumberOfUserDots) {
					maxNumberOfUserDots = currentNumberOfUserDots;
					bestTurn[0] = bestTurnHolder[0];
					bestTurn[1] = bestTurnHolder[1];
					bestTurn[2] = maxNumberOfUserDots;
				}

			}
			counter++;
		} while (counter <= map.length - dotsToWin);
		return bestTurn;
	}

	private static int[] aiBlocksUserOnOppositeDiagonal() {
		int[] bestTurn = new int[3];
		int maxNumberOfUserDots = 0;
		char[][] rotatedMap = rotateMap();
		int counter = dotsToWin - map.length;
		do {
			int[] bestTurnHolder = new int[2];
			int currentNumberOfUserDots = 0;
			int currentNumberOfEmptyDots = 0;
			boolean hasPriority = false;

			for (int i = 0; i < rotatedMap.length; i++) {
				for (int j = 0; j < rotatedMap.length; j++) {
					if (j - i == counter) {
						if (rotatedMap[i][j] == DOT_X) {
							currentNumberOfUserDots++;
						} else if (rotatedMap[i][j] == DOT_EMPTY) {
							if (((j > 0 && i > 0 && rotatedMap[i - 1][counter + i - 1] == DOT_X) && (j < rotatedMap.length - 1 && i < rotatedMap.length - 1 && rotatedMap[i + 1][counter + i + 1] == DOT_X))) {
								hasPriority = true;
								bestTurnHolder[0] = j;
								bestTurnHolder[1] = rotatedMap.length - 1 - i;
							} else if (((j > 0 && i > 0 && rotatedMap[i - 1][counter + i - 1] == DOT_X) || (j < rotatedMap.length - 1 && i < rotatedMap.length - 1 && rotatedMap[i + 1][counter + i + 1] == DOT_X)) && !hasPriority) {
								bestTurnHolder[0] = j;
								bestTurnHolder[1] = rotatedMap.length - 1 - i;
							}
							currentNumberOfEmptyDots++;
						} else {
							if (rotatedMap.length - currentNumberOfEmptyDots - currentNumberOfUserDots <= dotsToWin)
								break;
							currentNumberOfEmptyDots = 0;
							currentNumberOfUserDots = 0;
						}
					}
				}

				if (currentNumberOfUserDots >= maxNumberOfUserDots) {
					maxNumberOfUserDots = currentNumberOfUserDots;
					bestTurn[0] = bestTurnHolder[0];
					bestTurn[1] = bestTurnHolder[1];
					bestTurn[2] = maxNumberOfUserDots;
				}

			}
			counter++;
		} while (counter <= rotatedMap.length - dotsToWin);
		return bestTurn;
	}

	private static int[] aiPlanTurnOnRow() {
		int[] bestTurn = new int[3];
		int maxNumberOfAiDots = 0;

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
				bestTurn[0] = bestTurnHolder[0];
				bestTurn[1] = bestTurnHolder[1];
				bestTurn[2] = maxNumberOfAiDots;
			}
		}
		return bestTurn;
	}

	private static int[] aiPlanTurnOnColumn() {
		int[] bestTurn = new int[3];
		int maxNumberOfAiDots = 0;
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
				bestTurn[0] = bestTurnHolder[0];
				bestTurn[1] = bestTurnHolder[1];
				bestTurn[2] = maxNumberOfAiDots;
			}
		}
		return bestTurn;
	}

	private static int[] aiPlanTurnOnMainDiagonal() {
		int[] bestTurn = new int[3];
		int maxNumberOfAiDots = 0;
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
					bestTurn[0] = bestTurnHolder[0];
					bestTurn[1] = bestTurnHolder[1];
					bestTurn[2] = maxNumberOfAiDots;
				}
			}
			counter++;
		} while (counter <= map.length - dotsToWin);
		return bestTurn;
	}

	private static int[] aiPlanTurnOnOppositeDiagonal() {
		int[] bestTurn = new int[3];
		int maxNumberOfAiDots = 0;
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
					bestTurn[0] = bestTurnHolder[0];
					bestTurn[1] = bestTurnHolder[1];
					bestTurn[2] = maxNumberOfAiDots;
				}
			}
			counter++;
		} while (counter <= rotatedMap.length - dotsToWin);
		return bestTurn;
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
				if (x == -1 || map[x][y] != DOT_EMPTY) {
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
