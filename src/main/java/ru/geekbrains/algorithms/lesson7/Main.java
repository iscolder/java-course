package ru.geekbrains.algorithms.lesson7;

public class Main {


	public static void main(String[] args) {
		Graph graph = new Graph(5);
		graph.addEdge(1, 2);
		graph.addEdge(0, 4);
		graph.addEdge(1, 4);

		System.out.println(graph.getAdjList(1));
	}
}
