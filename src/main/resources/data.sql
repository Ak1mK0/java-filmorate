INSERT INTO Film_rating(rating_name)
VALUES
('G'),
('PG'),
('PG-13'),
('R'),
('NC-17');

INSERT INTO Genres(genre_name)
VALUES
('Комедия'),
('Драма'),
('Мультфильм'),
('Триллер'),
('Документальный'),
('Боевик');

INSERT INTO Films(film_name, film_description, film_release_date, film_duration, film_mpa_id)
VALUES
('Фильм1', 'Описание фильма 1', '1999-04-30', '90', '3'),
('Фильм2', 'Описание фильма 2', '1992-05-27', '120', '1'),
('Фильм3', 'Описание фильма 3', '2013-12-29', '30', '5'),
('Фильм4', 'Описание фильма 4', '1983-04-30', '48', '4');

INSERT INTO Users(user_email, user_login, user_name, user_birthday)
VALUES
('user1@mail.com', 'user1Login', 'user1Name', '1999-04-30'),
('user2@mail.com', 'user2Login', 'user2Name', '1999-05-30'),
('user3@mail.com', 'user3Login', 'user3Name', '1999-06-30');

INSERT INTO Friends(user_id, friend_id)
VALUES
('2', '3');