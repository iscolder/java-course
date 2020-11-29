package ru.geekbrains.algorithms.lesson3;

public class Expression {

	private final String exp;

	public Expression(String exp) {
		this.exp = exp;
	}

	public boolean checkBracket() {
		MyStack<Character> stack = new MyStack<>(exp.length()); // худший случай: все открывающие скобки
		for (int i = 0; i < exp.length(); i++) {
			char ch = exp.charAt(i);
			if (ch == '(' || ch == '[' || ch == '{') {
				stack.push(ch);
			} else if (ch == ')' || ch == ']' || ch == '}') {
				if (stack.isEmpty()) {
					System.out.println("Error in " + i + " position");
					return false;
				} else {
					char top = stack.pop();
					if (ch == ')' && top != '(' || ch == ']' && top != '[' || ch == '}' && top != '{') {
						System.out.println("Error in " + i + " position. Bracket does not match");
						return false;
					}
				}
			}
		}
		if (!stack.isEmpty()) {
			System.out.println("Error: not empty stack");
			return false;
		}
		return true;
	}
}
