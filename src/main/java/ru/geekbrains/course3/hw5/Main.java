package ru.geekbrains.course3.hw5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.Stream;

public class Main {
	public static final int CARS_COUNT = 4;
	private static final CountDownLatch finishLatch = new CountDownLatch(4);
	private static final ExecutorService executorService;
	private static final long start = System.nanoTime();

	static {
		executorService = Executors.newFixedThreadPool(4);
	}

	public static void main(String[] args) {
		System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
		Race race = new Race(new Road(60), new Tunnel(), new Road(40), new Finish());
		Car[] cars = new Car[CARS_COUNT];
		for (int i = 0; i < cars.length; i++) {
			cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
		}

		System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
		try {
			Stream.of(cars).forEach(executorService::execute);
			finishLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			executorService.shutdown();
		}
		System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
	}


	static class Car implements Runnable {
		private static int carNumber;

		static {
			carNumber = 0;
		}

		private final Race race;
		private final int speed;
		private final String name;

		public String getName() {
			return name;
		}

		public int getSpeed() {
			return speed;
		}

		public Car(Race race, int speed) {
			this.race = race;
			this.speed = speed;
			carNumber++;
			this.name = "Участник #" + carNumber;
		}

		@Override
		public void run() {
			try {
				System.out.println(this.name + " готовится");
				Thread.sleep(500 + (int) (Math.random() * 800));
				System.out.println(this.name + " готов");
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			for (int i = 0; i < race.getStages().size(); i++) {
				race.getStages().get(i).go(this);
			}
		}

		@Override
		public String toString() {
			return "Car{" +
					", speed=" + speed +
					", name='" + name + '\'' +
					'}';
		}
	}

	static abstract class Stage {
		protected int length;
		protected String description;

		public String getDescription() {
			return description;
		}

		public abstract void go(Car c);
	}

	static class Road extends Stage {
		public Road(int length) {
			this.length = length;
			this.description = "Дорога " + length + " метров";
		}

		@Override
		public void go(Car c) {
			try {
				System.out.println(c.getName() + " начал этап: " + description);
				Thread.sleep(length / c.getSpeed() * 1000L);
				System.out.println(c.getName() + " закончил этап: " + description);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	static class Finish extends Stage {

		@Override
		public void go(Car c) {
			finishLatch.countDown();
			System.out.println(c.getName() + " финишировал " + (CARS_COUNT - finishLatch.getCount()) + "-м, время: " + (System.nanoTime() - start) / 1000000 + " мс");
		}
	}

	static class Tunnel extends Stage {

		private final Semaphore tunnelSemaphore = new Semaphore(CARS_COUNT / 2, true);

		public Tunnel() {
			this.length = 80;
			this.description = "Тоннель " + length + " метров";
		}

		@Override
		public void go(Car c) {
			try {
				tunnelSemaphore.acquire();
				System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
				System.out.println(c.getName() + " начал этап: " + description);
				Thread.sleep(length / c.getSpeed() * 1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println(c.getName() + " закончил этап: " + description);
				tunnelSemaphore.release();
			}

		}
	}

	static class Race {
		private final List<Stage> stages;

		public List<Stage> getStages() {
			return stages;
		}

		public Race(Stage... stages) {
			this.stages = new ArrayList<>(Arrays.asList(stages));
		}
	}
}
