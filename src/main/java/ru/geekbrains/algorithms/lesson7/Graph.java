package ru.geekbrains.algorithms.lesson7;

import java.util.LinkedList;

public class Graph {
	private final int vertexCount;
	private int edgeCount;
	private final LinkedList<Integer>[] adjList;

	public Graph(int vertexCount) {
		if (vertexCount <= 0) {
			throw new IllegalArgumentException("Количество вершин должно быть натуральным числом");
		}
		this.vertexCount = vertexCount;
		adjList = new LinkedList[vertexCount];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new LinkedList<>();
		}
	}

	public LinkedList<Integer> getAdjList(int vertex) {
		return (LinkedList<Integer>) adjList[vertex].clone();
	}

	public void addEdge(int v1, int v2) {
		if (v1 < 0 || v2 < 0 || v1 >= vertexCount || v2 >= vertexCount) {
			throw new IllegalArgumentException();
		}
		adjList[v1].add(v2);
		edgeCount++;
		if (v1 == v2) {
			return;
		}
		adjList[v2].add(v1);
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public int getEdgeCount() {
		return edgeCount;
	}
}
