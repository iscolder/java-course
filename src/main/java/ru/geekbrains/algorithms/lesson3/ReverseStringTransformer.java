package ru.geekbrains.algorithms.lesson3;

public class ReverseStringTransformer {

	public String transform(String line) {
		if (line == null || line.isEmpty()) {
			return "";
		}
		char[] symbols = line.toCharArray();
		MyStack<Character> stack = new MyStack<>(symbols.length);
		for (char symbol : symbols) {
			stack.push(symbol);
		}
		char[] reverseSymbols = new char[symbols.length];
		int i = 0;
		while (!stack.isEmpty()) {
			reverseSymbols[i++] = stack.pop();
		}
		StringBuilder sb = new StringBuilder();
		for (char ch : reverseSymbols) {
			sb.append(ch);
		}
		return sb.toString();
	}

}
