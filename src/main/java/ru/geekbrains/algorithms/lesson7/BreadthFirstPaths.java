package ru.geekbrains.algorithms.lesson7;

import java.util.LinkedList;

public class BreadthFirstPaths {
	private final boolean[] marked;
	private final int[] edgeTo;
	private final int source;

	public BreadthFirstPaths(Graph g, int source) {
		this.source = source;
		edgeTo = new int[g.getVertexCount()];
		marked = new boolean[g.getVertexCount()];
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


	public boolean hasPathTo(int dist) {
		return marked[dist];
	}

	public LinkedList<Integer> pathTo(int dist) {
		if (!hasPathTo(dist)) {
			return null;
		}
		LinkedList<Integer> stack = new LinkedList<>();
		int vertex = dist;
		while (vertex != source) {
			stack.push(vertex);
			vertex = edgeTo[vertex];
		}
		return stack;
	}

}
