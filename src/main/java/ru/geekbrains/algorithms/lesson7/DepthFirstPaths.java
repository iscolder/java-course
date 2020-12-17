package ru.geekbrains.algorithms.lesson7;

import java.util.LinkedList;

public class DepthFirstPaths {
	private final boolean[] marked;
	private final int[] parents;
	private final int source;

	public DepthFirstPaths(Graph g, int source) {
		this.source = source;
		marked = new boolean[g.getVertexCount()];
		parents = new int[g.getVertexCount()];
		dfs(g, source);
	}

	private void dfs(Graph g, int v) {
		marked[v] = true;
		for (int w : g.getAdjList(v)) {
			if (!marked[w]) {
				parents[w] = v;
				dfs(g, w);
			}
		}
	}

	public LinkedList<Integer> pathTo(int dist) {
		if (!marked[dist]) {
			return null;
		}
		LinkedList<Integer> stack = new LinkedList<>();
		int current = source;
		while (current != dist) {
			stack.push(current);
			current = parents[current];
		}
		return stack;
	}
}
