DROP ALL OBJECTS;

CREATE TABLE IF NOT EXISTS Users (
    user_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_email VARCHAR(255) NOT NULL UNIQUE,
    user_login VARCHAR(255) NOT NULL UNIQUE,
    user_name VARCHAR(255),
    user_birthday DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS Friends (
    user_id INTEGER NOT NULL,
    friend_id INTEGER NOT NULL,
    friend_confirm BOOLEAN,
    PRIMARY KEY (user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (friend_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    CHECK (user_id != friend_id)
);

CREATE TABLE IF NOT EXISTS Film_rating (
    rating_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    rating_name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Films (
    film_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    film_name VARCHAR(255) NOT NULL,
    film_description VARCHAR(200) NOT NULL,
    film_duration INTEGER CHECK (film_duration > 0),
    film_releasedate DATE NOT NULL,
    film_rating_id INTEGER NOT NULL,
    FOREIGN KEY (film_rating_id) REFERENCES Film_rating(rating_id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS Film_like (
    user_id INTEGER NOT NULL,
    film_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, film_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (film_id) REFERENCES Films(film_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Genres (
    genre_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    genre_name VARCHAR(40) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Film_genres (
    film_id INTEGER NOT NULL,
    genre_id INTEGER NOT NULL,
    PRIMARY KEY (film_id, genre_id),
    FOREIGN KEY (film_id) REFERENCES Films(film_id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES Genres(genre_id) ON DELETE RESTRICT
);