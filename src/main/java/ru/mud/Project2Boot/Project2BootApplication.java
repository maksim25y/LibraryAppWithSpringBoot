package ru.mud.Project2Boot;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SpringBootApplication
public class Project2BootApplication {
	@PostConstruct
	public void init() {
		String sql = "create table person\n" +
				"(\n" +
				"    id       serial\n" +
				"        primary key,\n" +
				"    info     varchar\n" +
				"        unique,\n" +
				"    birthday integer not null\n" +
				"        constraint person_birthday_check\n" +
				"            check ((birthday > 1900) AND (birthday <= 2024))\n" +
				");";
		String sql2 = "" +
				"create table book\n" +
				"(\n" +
				"    id  serial primary key,\n" +
				"    name varchar not null,\n" +
				"    author varchar not null,\n" +
				"    date integer not null constraint book_date_check check ((date >= 0) AND (date <= 2024)),\n" +
				"    user_id integer references person on delete set default,\n" +
				"    taken timestamp\n" +
				");";
		String sqlValues = "INSERT INTO person (info, birthday) VALUES ('Иванов Иван Иванович', 1990);\n" +
				"INSERT INTO person (info, birthday) VALUES ('Иванов Сергей Иванович', 1985);\n" +
				"INSERT INTO person (info, birthday) VALUES ('Ломов Петр Романович', 1978);\n" +
				"INSERT INTO person (info, birthday) VALUES ('Дронов Алексей Дмимтриевич', 1995);\n" +
				"INSERT INTO person (info, birthday) VALUES ('Шолип Олег Иванович', 2000);\n" +
				"INSERT INTO person (info, birthday) VALUES ('Лытнева Владислава Анатольевна', 1980);\n" +
				"INSERT INTO person (info, birthday) VALUES ('Иночкина Екатерина Вительевна', 1965);\n" +
				"INSERT INTO person (info, birthday) VALUES ('Марков Виталий Николаевич', 1989);\n" +
				"INSERT INTO person (info, birthday) VALUES ('Мурлина Татьяна Ивановна', 1987);\n" +
				"INSERT INTO person (info, birthday) VALUES ('Трофимов Виктор Маратыч', 1992);" +
				"INSERT INTO book (name, author, date, user_id, taken) VALUES ('Война и мир', 'Лев Толстой', 1869, 1, '2024-02-29 10:00:00');\n" +
				"INSERT INTO book (name, author, date, user_id, taken) VALUES ('Преступление и наказание', 'Федор Достоевский', 1866, 2, '2024-02-29 11:00:00');\n" +
				"INSERT INTO book (name, author, date, user_id, taken) VALUES ('Мастер и Маргарита', 'Михаил Булгаков', 1967, 3, '2024-02-29 12:00:00');\n" +
				"INSERT INTO book (name, author, date, user_id, taken) VALUES ('Анна Каренина', 'Лев Толстой', 1877, 4, '2024-02-29 13:00:00');\n" +
				"INSERT INTO book (name, author, date, user_id, taken) VALUES ('Бесы', 'Федор Достоевский', 1872, 5, '2024-02-29 14:00:00');\n" +
				"INSERT INTO book (name, author, date, user_id, taken) VALUES ('Мёртвые души', 'Николай Гоголь', 1842, 6, '2024-02-29 15:00:00');\n";
		String url = "jdbc:postgresql://db:5432/postgres";
		String user = "admin";
		String password = "root";
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		try (Connection connection = DriverManager.getConnection(url, user, password);
			 PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try (Connection connection = DriverManager.getConnection(url, user, password);
			 PreparedStatement statement = connection.prepareStatement(sql2)) {
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (Connection connection = DriverManager.getConnection(url, user, password);
			 PreparedStatement statement = connection.prepareStatement(sqlValues)) {
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(Project2BootApplication.class, args);
	}

}
