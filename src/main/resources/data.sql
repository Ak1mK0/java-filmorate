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

INSERT INTO Film_genres(film_id, genre_id)
VALUES
('1', '1'),
('1', '2'),
('2', '5'),
('2', '1'),
('3', '6');

INSERT INTO Users(user_email, user_login, user_name, user_birthday)
VALUES
('u1.@mail.com', 'u1Login', 'u1Name', '1992-05-18'),
('u2.@mail.com', 'u2Login', 'u2Name', '1998-8-24'),
('u3.@mail.com', 'u3Login', 'u3Name', '2003-1-05');

INSERT INTO Film_like(user_id, film_id)
VALUES
('1', '2');