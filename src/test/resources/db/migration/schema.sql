DROP SCHEMA IF EXISTS hotelSchema CASCADE;
CREATE SCHEMA IF NOT EXISTS hotelSchema;

-- Création de la table "users"
CREATE TABLE hotelSchema.users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_admin BOOLEAN NOT NULL
);

-- Création de la table "rooms"
CREATE TABLE hotelSchema.rooms (
    id SERIAL PRIMARY KEY,
    room_number VARCHAR(255) NOT NULL,
    available BOOLEAN NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    images VARCHAR(255)[],
    description TEXT
);

-- Création de la table "reservations"
CREATE TABLE hotelSchema.reservations (
    id SERIAL PRIMARY KEY,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    room_id BIGINT REFERENCES hotelSchema.rooms(id) ON DELETE CASCADE,
    user_id BIGINT REFERENCES hotelSchema.users(id) ON DELETE CASCADE
);

-- Ajout d'index si nécessaire
CREATE INDEX idx_room_id ON hotelSchema.reservations(room_id);
CREATE INDEX idx_user_id ON hotelSchema.reservations(user_id);
