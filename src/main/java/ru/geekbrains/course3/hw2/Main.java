package ru.geekbrains.course3.hw2;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

	public static void main(String[] args) {
		deleteUserTable();
		createUserTable();
		insertUsers();
		List<User> users = findAllUsers();
		System.out.println(users);

		System.out.println("Petya auth: " + authenticate("Petya", "e2te22"));
		System.out.println("Kostya auth: " + authenticate("Kostya", "2222"));

		changeNick("Petya", "Hulk");
		changeNick("Vasya", "Iron Man");

		users = findAllUsers();
		System.out.println(users);
	}

	private static boolean authenticate(String name, String password) {
		if (name == null || name.trim().length() == 0 || password == null || password.trim().length() == 0) {
			return false;
		}
		IDbClient dbCall = new DbClient(
				"com.mysql.cj.jdbc.Driver",
				"jdbc:mysql://localhost:3306/test",
				"root",
				"root"
		);

		AtomicBoolean authenticated = new AtomicBoolean(false);
		dbCall.get("SELECT count(id) as total FROM users WHERE name = '" + name + "' and password = '" + password + "'",
				resultSet -> {
					try {
						if (resultSet.next()) {
							int count = resultSet.getInt("total");
							authenticated.set(count == 1);
						}
					} catch (SQLException e) {
						throw new RuntimeException(e.getMessage());
					}
				});
		return authenticated.get();
	}

	private static void changeNick(String name, String newNick) {
		if (name == null || name.trim().length() == 0 || newNick == null || newNick.trim().length() == 0) {
			return;
		}
		IDbClient dbCall = new DbClient(
				"com.mysql.cj.jdbc.Driver",
				"jdbc:mysql://localhost:3306/test",
				"root",
				"root"
		);
		dbCall.update("UPDATE users SET nick = '" + newNick + "' WHERE name = '" + name + "'");
	}

	private static void createUserTable() {
		IDbClient dbCall = new DbClient(
				"com.mysql.cj.jdbc.Driver",
				"jdbc:mysql://localhost:3306/test",
				"root",
				"root"
		);
		dbCall.createTable(
				"CREATE TABLE if not exists users (" +
						"id int PRIMARY KEY AUTO_INCREMENT, " +
						"name varchar(20), " +
						"phone int," +
						"nick varchar(20) unique," +
						"password varchar(20)" +
						");"
		);
	}

	private static void insertUsers() {
		IDbClient dbCall = new DbClient(
				"com.mysql.cj.jdbc.Driver",
				"jdbc:mysql://localhost:3306/test",
				"root",
				"root"
		);
		dbCall.insert(Arrays.asList(
				"INSERT INTO users(name, phone, nick, password) VALUES ('Petya', 12345, 'Spiderman', 'e2te22')",
				"INSERT INTO users(name, phone, nick, password) VALUES ('Vasya', 12345, 'Batman', 'dq22')",
				"INSERT INTO users(name, phone, nick, password) VALUES ('IURII', 12345, 'Superman', 'dd33aa')")
		);
	}

	private static List<User> findAllUsers() {
		IDbClient dbCall = new DbClient(
				"com.mysql.cj.jdbc.Driver",
				"jdbc:mysql://localhost:3306/test",
				"root",
				"root"
		);

		List<User> users = new ArrayList<>();
		dbCall.get("SELECT * FROM users",
				resultSet -> {
					try {
						while (resultSet.next()) {
							String name = resultSet.getString("name");
							String nick = resultSet.getString("nick");
							int phone = resultSet.getInt("phone");
							users.add(new User(name, nick, phone));
						}
					} catch (SQLException e) {
						throw new RuntimeException(e.getMessage());
					}
				});
		return users;
	}

	private static void deleteUsers() {
		IDbClient dbCall = new DbClient(
				"com.mysql.cj.jdbc.Driver",
				"jdbc:mysql://localhost:3306/test",
				"root",
				"root"
		);
		dbCall.delete("DELETE FROM users");
	}

	private static void updateUsers() {
		IDbClient dbCall = new DbClient(
				"com.mysql.cj.jdbc.Driver",
				"jdbc:mysql://localhost:3306/test",
				"root",
				"root"
		);
		dbCall.update("UPDATE users SET phone=55555 WHERE name='Petya'");
	}

	private static void deleteUserTable() {
		IDbClient dbCall = new DbClient(
				"com.mysql.cj.jdbc.Driver",
				"jdbc:mysql://localhost:3306/test",
				"root",
				"root"
		);
		dbCall.deleteTable("DROP table if exists users");
	}

	static class User {
		private final String name;
		private final String nick;
		private final int phone;

		User(String name, String nick, int phone) {
			this.name = name;
			this.nick = nick;
			this.phone = phone;
		}

		@Override
		public String toString() {
			return "User{" +
					"name='" + name + '\'' +
					", nick='" + nick + '\'' +
					", phone=" + phone +
					'}';
		}
	}
}
