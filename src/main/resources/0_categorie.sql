DROP TABLE IF EXISTS category;
CREATE TABLE category
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO category (name)
VALUES ('Lighting'),
       ('Cables'),
       ('Panels');
