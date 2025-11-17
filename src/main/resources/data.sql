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

INSERT INTO Films(film_name, film_description, film_duration, film_releasedate, film_rating_id)
VALUES
('Фильм1', 'Описание фильма 1', 90, '1999-04-30', 3),
('Фильм2', 'Описание фильма 2', 120, '1992-05-27', 1),
('Фильм3', 'Описание фильма 3', 30, '2013-12-29', 5),
('Фильм4', 'Описание фильма 4', 48, '1983-04-30', 4);