DROP TABLE IF EXISTS product;

CREATE TABLE product
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255)    NOT NULL,
    description TEXT,
    price       DECIMAL(10, 2)  NOT NULL,
    category_id BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category (id)
);

INSERT INTO product (name, description, price, category_id)
VALUES ('Lamp A', 'Bright lamp', 50.0, 1),
       ('Cable B', 'High-quality cable', 10.0, 2);
