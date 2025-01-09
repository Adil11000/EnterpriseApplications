USE db;

DROP TABLE IF EXISTS reservation_item;
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS cart_item;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS category;

CREATE TABLE category
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO category (name)
VALUES ('Lighting Equipment'),
       ('Stage Elements'),
       ('Light Panels'),
       ('Cables and Accessories'),
       ('Audio Equipment'),
       ('Visual Equipment'),
       ('Projection Equipment');


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
VALUES
    -- Lighting Equipment
    ('LED Spotlight', 'High-intensity LED spotlight for stage use.', 199.99, 1),
    ('Portable Floodlight', 'Compact floodlight with adjustable brightness.', 89.99, 1),
    ('Stage Lighting Kit', 'Complete kit with multiple lights and stands.', 499.99, 1),
    ('Wireless DMX Controller', 'Remote control for stage lighting setups.', 149.99, 1),
    ('Colored Gels Pack', 'Set of colored gels for lighting effects.', 29.99, 1),

    -- Stage Elements
    ('Modular Stage Platform', 'Interlocking stage platforms for custom setups.', 299.99, 2),
    ('Stage Ramp', 'Portable ramp for stage accessibility.', 249.99, 2),
    ('Backdrop Frame', 'Adjustable frame for stage backdrops.', 159.99, 2),
    ('Stage Stairs', 'Durable stairs for stage access.', 199.99, 2),
    ('Safety Rails', 'Safety railings for stage platforms.', 99.99, 2),

    -- Light Panels
    ('LED Light Panel', 'Thin, high-output LED panel.', 349.99, 3),
    ('Softbox Kit', 'Softbox with adjustable light panel.', 129.99, 3),
    ('Portable Light Panel', 'Battery-operated light panel for on-the-go use.', 179.99, 3),
    ('RGB Light Panel', 'Full-spectrum RGB panel for creative lighting.', 229.99, 3),
    ('Light Diffuser', 'Attachment to soften panel lighting.', 49.99, 3),

    -- Cables and Accessories
    ('XLR Cable', 'Professional audio cable for microphones.', 19.99, 4),
    ('HDMI Cable', 'High-speed HDMI cable for video transmission.', 14.99, 4),
    ('Power Extension Cable', 'Heavy-duty extension cord for stage setups.', 24.99, 4),
    ('Cable Organizer', 'Set of reusable cable ties.', 9.99, 4),
    ('DMX Cable', 'Cable for connecting lighting equipment.', 17.99, 4),

    -- Audio Equipment
    ('Dynamic Microphone', 'Durable microphone for live performances.', 89.99, 5),
    ('Stage Monitor Speaker', 'High-quality monitor for stage use.', 249.99, 5),
    ('Audio Mixer', 'Compact audio mixer with multiple channels.', 399.99, 5),
    ('Wireless Microphone System', 'Professional wireless mic setup.', 299.99, 5),
    ('Headphones', 'Studio-quality headphones for monitoring.', 129.99, 5),

    -- Visual Equipment
    ('4K Camera', 'High-definition camera for capturing video.', 1299.99, 6),
    ('Camera Tripod', 'Sturdy tripod for cameras.', 89.99, 6),
    ('Ring Light', 'LED ring light for photography.', 59.99, 6),
    ('Action Camera', 'Compact camera for dynamic shooting.', 199.99, 6),
    ('Green Screen Kit', 'Complete kit for chroma key setups.', 149.99, 6),

    -- Projection Equipment
    ('Digital Projector', 'High-resolution digital projector.', 799.99, 7),
    ('Projection Screen', 'Foldable projection screen.', 129.99, 7),
    ('Ceiling Mount', 'Adjustable ceiling mount for projectors.', 49.99, 7),
    ('Laser Pointer', 'High-precision pointer for presentations.', 19.99, 7),
    ('Wireless HDMI Transmitter', 'Wireless solution for projector setups.', 99.99, 7);


CREATE TABLE user
(
    id              SERIAL PRIMARY KEY,
    email           VARCHAR(255) NOT NULL,
    hashed_password VARCHAR(255) NOT NULL,
    role            VARCHAR(255) NOT NULL
);

CREATE TABLE cart
(
    id      SERIAL PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE cart_item
(
    id         SERIAL PRIMARY KEY,
    product_id BIGINT UNSIGNED NOT NULL,
    quantity   INT UNSIGNED    NOT NULL,
    cart_id    BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (cart_id) REFERENCES cart (id)
);

CREATE TABLE reservation
(
    id             SERIAL PRIMARY KEY,
    user_id        BIGINT UNSIGNED NOT NULL,
    reserved_until DATETIME        NOT NULL,
    returned       BOOLEAN         NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE reservation_item
(
    id             SERIAL PRIMARY KEY,
    reservation_id BIGINT UNSIGNED NOT NULL,
    product_id     BIGINT UNSIGNED NOT NULL,
    quantity       INT UNSIGNED    NOT NULL,
    FOREIGN KEY (reservation_id) REFERENCES reservation (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);
