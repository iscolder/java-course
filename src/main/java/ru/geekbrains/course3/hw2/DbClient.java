package ru.geekbrains.course3.hw2;

import java.sql.*;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class DbClient implements IDbClient {

	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;

	private final String driver;
	private final String url;
	private final String user;
	private final String password;

	public DbClient(String driver, String url, String user, String password) {
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;
	}

	@Override
	public void createTable(String query) {
		insert(Collections.singletonList(query));
	}

	@Override
	public void deleteTable(String query) {
		delete(query);
	}

	@Override
	public void insert(List<String> queries) {
		try {
			connect(driver, url);
			write(queries);
		} finally {
			close();
		}
	}

	@Override
	public void get(String query, Consumer<ResultSet> resultSetHandler) {
		try {
			connect(driver, url);
			read(query, resultSetHandler);
		} finally {
			close();
		}
	}

	@Override
	public void update(String query) {
		try {
			connect(driver, url);
			updateInternal(query);
		} finally {
			close();
		}
	}

	@Override
	public void delete(String query) {
		try {
			connect(driver, url);
			deleteInternal(query);
		} finally {
			close();
		}
	}

	private void connect(String driver, String url) {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException("Cannot connect to DB: " + e.getMessage());
		}
	}

	private void write(List<String> queries) {
		try {
			statement = connection.createStatement();
			queries.forEach(query -> {
				try {
					statement.execute(query);
				} catch (SQLException e) {
					throw new RuntimeException("Cannot execute query: " + e.getMessage());
				}
			});
		} catch (SQLException e) {
			throw new RuntimeException("Cannot get statement: " + e.getMessage());
		}
	}

	private void updateInternal(String query) {
		try {
			statement = connection.createStatement();
			statement.execute(query);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private void deleteInternal(String query) {
		try {
			statement = connection.createStatement();
			statement.execute(query);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private void read(String sql, Consumer<ResultSet> resultSetHandler) {
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			resultSetHandler.accept(resultSet);
		} catch (SQLException e) {
			throw new RuntimeException("Cannot execute query: " + e.getMessage());
		}
	}

	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("Cannot close resource: " + e.getMessage());
		}
	}

}
