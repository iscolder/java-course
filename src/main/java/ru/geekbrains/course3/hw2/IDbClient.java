package ru.geekbrains.course3.hw2;

import java.sql.ResultSet;
import java.util.List;
import java.util.function.Consumer;

public interface IDbClient {
	void createTable(String query);
	void deleteTable(String query);
	void insert(List<String> queries);
	void get(String query, Consumer<ResultSet> resultSetHandler);
	void update(String query);
	void delete(String query);
}
