Данный проект представляет из себя веб-приложение (система учета книг в бибилиотеке).

<details><summary>Функционал</summary>
На главной странице расположен блок с кнопками и хэдер. При нажатии на  кнопку "Список людей в библиотеке" происходит переход на страницу (/people) со списком людей в библиотеке.

![image](https://github.com/maksim25y/LibraryApp/assets/131711956/29066f2d-005f-4756-ae76-6a87ceae58cf)
Есть возможность добавления человека в базу данных библиотеки, необходимо нажать на кнопку "Добавить", после чего происходит переход на страницу с адресом (/people/new).

![image](https://github.com/maksim25y/LibraryApp/assets/131711956/16966b58-36cc-4759-b8d6-a47e9a0ec346)
Поля в форме имеют валидацию, поэтому ввести некорректные значения нельзя (в таком случае будет выведена ошибка).
Ограничения на ввод: 
1) Возраст должен быть в следующем диапазоне (1900-2024]

![image](https://github.com/maksim25y/LibraryApp/assets/131711956/5db855fa-c952-46a4-8830-a0d983229f9b)

2) ФИО должно быть по следующему шаблону [Фамилия Имя Отчество], причем на русском языке
![image](https://github.com/maksim25y/LibraryApp/assets/131711956/42998745-7f27-4bc6-9646-85f67ae6363f)
3) Поля не должны быть пустыми 
4) ФИО должно быть уникальным

![image](https://github.com/maksim25y/LibraryApp/assets/131711956/f6bd633b-d37c-4da5-82ac-11b052afd177)

![image](https://github.com/maksim25y/LibraryApp/assets/131711956/080c09d8-d87c-48a9-a1a2-a07d9c897403)

При нажатии на пользователя происходит переход на страницу (/people/{id}) с информацией о пользователе (также и список его книг). Если книга была взята более 10 дней назад, то она считается просроченой и подсвечивается красным цветом.

![image](https://github.com/maksim25y/LibraryApp/assets/131711956/fe8e1073-de7c-4b34-a104-402991b2ab84)

Если пользователь брал книги, то все они будут выведены, в противном случае будет надпись "У человека нет книг".

![image](https://github.com/maksim25y/LibraryApp/assets/131711956/9a9d0e2c-261b-4d22-9585-73c8d640f905)

При нажатии на кнопку "Редактировать" происходит переход на страницу (/people/{id}/edit) с редактированием информации.
Здесь также присутствует валидация.

![image](https://github.com/maksim25y/LibraryApp/assets/131711956/e47dac9b-f86d-47fb-8ecc-b40a16a454ce)

При нажатии на кнопку "Удалить" происходит удаление пользователя из базы данных, книги, которые были у пользователя становятся свободными.

При переходе по адресу /books происходит получение страницы со всеми книгами, которые есть в БД библиотеки.

![image](https://github.com/maksim25y/LibraryApp/assets/131711956/4aee006c-8b9e-4764-897d-21375879d4e4)

При добавлении параметра запроса ?sort_by_year=true книги будут отсортированы по году выпуска

![image](https://github.com/maksim25y/LibraryApp/assets/131711956/7fa5083a-260d-43af-b5d2-7ec31039efe1)

При нажатии на кнопку "Добавить" произойдет переадресация на страницу (/books/new) добавления новой книги. 
Все поля в форме имеют следующую валидацию: 
1) Название книги должно быть на русском языке

![image](https://github.com/maksim25y/LibraryApp/assets/131711956/75ca3528-8f19-41e0-a335-10f2b97c1b2f)

2) Автор должен быть указан по шаблону [Имя Отчество Фамилия] + на русском языке
![image](https://github.com/maksim25y/LibraryApp/assets/131711956/c8fd65b5-b315-4455-8a0f-5fce9a760c5c)

3) Дата выпуска книги в диапазоне (0-2024]
![image](https://github.com/maksim25y/LibraryApp/assets/131711956/4bdffc3a-1621-4821-880e-8bd1e8f3d86a)
4) Значения не могут быть пустыми

При добавлении параметров запроса ?page=n&page_per_page=m книги будет получена n-ая страница, на которой будут расположены m книг. Пример работы с набором параметров page=1&page_per_page=2:

![image](https://github.com/maksim25y/LibraryApp/assets/131711956/022f61b2-fbf4-416f-8f3e-f0ecc2dad465)

При переходе по адресу (/books/{id}) будет получена информация о книге и о ее текущем владельце (если он есть):

![image](https://github.com/maksim25y/LibraryApp/assets/131711956/4dea8e9a-8ab4-4320-a9ed-1109aee715f8)

При нажатии на кнопку "Освободить" книга не будет иметь владельца, появится меню с выбором нового владельца (можно выбрать нового владельца - при нажатии книга будет назначена ему).

![image](https://github.com/maksim25y/LibraryApp/assets/131711956/d4e24990-d9c5-4d9f-9bcd-c2cef77fe3b2)

При нажатии на кнопку редактирования будет переход на страницу (/books/{id}/edit) с редактированием информации о книге, все поля имеют валидацию.

![image](https://github.com/maksim25y/LibraryApp/assets/131711956/f8671d07-04ba-4b6a-bbc5-5cc23588ee8a)

При нажатии на кнопку "Удалить" книга будет удалена из базы данных.

При переходе по адресу /books/search будет получена страница с поиском книг. При вводе названия книги (частичное название тоже подойдет) будет получена книга (вместе с текущим владельцем книги, если он есть (в противном случае будет надпись "Книга свободна")), наиболее подходящая под запрос:

![image](https://github.com/maksim25y/LibraryApp/assets/131711956/fc06b827-f27b-40ee-ac94-069850ddf433)

![image](https://github.com/maksim25y/LibraryApp/assets/131711956/be282c88-9f64-49b2-bf47-062148fb6e08)

![image](https://github.com/maksim25y/LibraryApp/assets/131711956/0bf5b813-7404-4cb8-a0aa-36928139368b)

![image](https://github.com/maksim25y/LibraryApp/assets/131711956/316024a6-13f0-47b2-913e-fcf8a82d0256)

![image](https://github.com/maksim25y/LibraryApp/assets/131711956/c71df1de-8f92-4dfe-aeaf-353b26c362cc)
</details>
<details><summary>Реализация</summary>
Во время выполнения проекта я использовал следующий набор технологий: PostgreSQL, ApacheTomcat, Maven, Spring MVC, Spring Data JPA, ORM Hibernate, Bootstrap, Thymeleaf, Spring Boot, Docker.
Были написаны unit - тесты и интеграционные тесты для контроллеров.
Произведена настройка Github Actions.
  
База данных:

![image](https://github.com/maksim25y/LibraryApp/assets/131711956/26ca6a28-b8d3-4a2a-bd34-75e05d0b440c)

</details>
<details><summary>Запуск</summary>
Для того, чтобы запустить необходимо проделать следующие шаги на Windows, установите [Git Bash](https://git-scm.com/)

1. Склонируйте репозиторий

```shell
git clone https://github.com/maksim25y/LibraryAppWithSpringBoot.git
```

2. Скачайте и установите Docker

Скачать и найти инструкцию по установке вы можете на официальном сайте [Docker](https://www.docker.com)

3. Запустите сайт в Docker

Для этого откройте терминал и перейдите в папку репозитория

```shell
cd LibraryAppWithSpringBoot
```

#### Переменные окружения в .env

Описание:
1. POSTGRES_USER - логин для БД
2. POSTGRES_PASSWORD - пароль от базы данных
3. SPRING_DATASOURCE_URL - адрес БД
4. SPRING_DATASOURCE_USERNAME - логин для БД, но для Spring
5. SPRING_DATASOURCE_PASSWORD - пароль для БД, но для Spring

Далее введите команду

```shell
docker-compose up --build
```
Готово! Сервер запущен.
Чтобы зайти на сайт перейдите по ссылке: localhost:8080

Чтобы остановить работу контейнеров, в терминале, откуда вы запускали docker-compose нажмите Ctrl+C (Control + C для Mac)
</details>

