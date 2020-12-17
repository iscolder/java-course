package ru.geekbrains.algorithms.lesson7;

public class Main {

	public static void main(String[] args) {
		Graph graph = new Graph(10);
		graph.addEdge(1, 2);
		graph.addEdge(0, 4);
		graph.addEdge(1, 4);
		graph.addEdge(2, 3);
		graph.addEdge(3, 9);
		graph.addEdge(8, 4);
		graph.addEdge(7, 1);
		graph.addEdge(5, 3);
		graph.addEdge(6, 0);
		BreadthFirstPaths breadthFirstPaths = new BreadthFirstPaths(graph, 1);
		System.out.println(breadthFirstPaths.pathTo(6));
	}
}
