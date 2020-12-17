package ru.geekbrains.algorithms.lesson7;

import java.util.LinkedList;

public class BreadthFirstPaths extends AbstractFirstPaths {

	public BreadthFirstPaths(Graph g, int source) {
		super(g, source);
		bfs(g, source);
	}

	private void bfs(Graph g, int source) {
		LinkedList<Integer> queue = new LinkedList<>();
		queue.addLast(source);
		marked[source] = true;
		while (!queue.isEmpty()) {
			int vertex = queue.removeFirst();
			for (int w : g.getAdjList(vertex)) {
				if (!marked[w]) {
					marked[w] = true;
					edgeTo[w] = vertex;
					queue.addLast(w);
				}
			}
		}
	}
}
