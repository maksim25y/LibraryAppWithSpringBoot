create table person
	(
	id  serial primary key,
	info varchar unique,
	birthday integer not null constraint person_birthday_check check ((birthday > 1900) AND (birthday <= 2024)));

create table book
	(
	id  serial primary key,
	name varchar not null,
	author varchar not null,
	date integer not null constraint book_date_check check ((date >= 0) AND (date <= 2024)),
	user_id integer references person on delete set default,
	taken timestamp
	);
INSERT INTO person (info, birthday) VALUES ('Иванов Иван Иванович', 1990);
				INSERT INTO person (info, birthday) VALUES ('Иванов Сергей Иванович', 1985);
				INSERT INTO person (info, birthday) VALUES ('Ломов Петр Романович', 1978);
				INSERT INTO person (info, birthday) VALUES ('Дронов Алексей Дмимтриевич', 1995);
				INSERT INTO person (info, birthday) VALUES ('Шолип Олег Иванович', 2000);
				INSERT INTO person (info, birthday) VALUES ('Лытнева Владислава Анатольевна', 1980);
				INSERT INTO person (info, birthday) VALUES ('Иночкина Екатерина Вительевна', 1965);
				INSERT INTO person (info, birthday) VALUES ('Марков Виталий Николаевич', 1989);
				INSERT INTO person (info, birthday) VALUES ('Мурлина Татьяна Ивановна', 1987);
				INSERT INTO person (info, birthday) VALUES ('Трофимов Виктор Маратыч', 1992);
				INSERT INTO book (name, author, date, user_id, taken) VALUES ('Война и мир', 'Иванов Сергей Иванович', 1869, 1, '2024-02-29 10:00:00');
				INSERT INTO book (name, author, date, user_id, taken) VALUES ('Преступление и наказание', 'Иванов Сергей Иванович', 1866, 2, '2024-02-29 11:00:00');
				INSERT INTO book (name, author, date, user_id, taken) VALUES ('Мастер и Маргарита', 'Иванов Сергей Иванович', 1967, 3, '2024-02-29 12:00:00');
				INSERT INTO book (name, author, date, user_id, taken) VALUES ('Анна Каренина', 'Иванов Сергей Иванович', 1877, 4, '2024-02-29 13:00:00');
				INSERT INTO book (name, author, date, user_id, taken) VALUES ('Бесы', 'Иванов Сергей Иванович', 1872, 5, '2024-02-29 14:00:00');
				INSERT INTO book (name, author, date, user_id, taken) VALUES ('Мёртвые души', 'Иванов Сергей Иванович', 1842, 6, '2024-02-29 15:00:00');