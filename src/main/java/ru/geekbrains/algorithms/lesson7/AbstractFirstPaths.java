package ru.geekbrains.algorithms.lesson7;

import java.util.LinkedList;

public abstract class AbstractFirstPaths {
	protected boolean[] marked;
	protected int[] edgeTo;
	protected int source;

	public AbstractFirstPaths(Graph g, int source) {
		this.source = source;
		edgeTo = new int[g.getVertexCount()];
		marked = new boolean[g.getVertexCount()];
	}

	private boolean hasPathTo(int dist) {
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
