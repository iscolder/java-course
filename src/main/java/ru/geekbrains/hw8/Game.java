package ru.geekbrains.hw8;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Game extends JFrame {

	private static Game game;

	private static Image background;
	private static Image plane;

	private static float planeLeft = 200;
	private static float planeTop = 100;

	private static long lastFrameTime = System.nanoTime();

	private static int posX = 0;
	private static int posY = 0;

	private static float xk;
	private static float yk;

	public static void main(String[] args) throws IOException {
		init();
		play();
	}

	private static void init() throws IOException {
		background = ImageIO.read(Game.class.getResourceAsStream("background.png"));
		plane = ImageIO.read(Game.class.getResourceAsStream("plane.png"));

		game = new Game();
		game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		game.setLocation(200, 100);
		game.setSize(906, 478);
		game.setResizable(false);
	}

	private static void play() {
		GameField gameField = new GameField();
		gameField.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				posX = e.getX();
				posY = e.getY();
				xk = (posY - planeTop);
				yk = (posX - planeLeft);
			}
		});

		game.add(gameField);
		game.setVisible(true);
	}

	private static class GameField extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			onRepaint(g);
			repaint();
		}
	}

	private static void onRepaint(Graphics g) {
		long currentTime = System.nanoTime();
		float delta = (currentTime - lastFrameTime) * 0.000000001f;
		lastFrameTime = currentTime;

		planeTop = delta * xk  + planeTop;
		planeLeft = delta * yk  + planeLeft;

		if (Math.abs(planeLeft - posX) <= 1 && Math.abs(planeTop - posY) <= 1) { // stop plane condition
			xk = 0;
			yk = 0;
		}

		g.drawImage(background, 0, 0, null);
		g.drawImage(plane, (int) planeLeft, (int) planeTop, null);
	}
}
