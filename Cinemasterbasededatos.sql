
CREATE DATABASE Cinemaster;



CREATE TABLE peliculas (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    genero VARCHAR(50),
    duracion INTEGER,
    clasificacion VARCHAR(20),
    director VARCHAR(100),
    sinopsis TEXT
);

CREATE TABLE salas (
    id SERIAL PRIMARY KEY,
    numero INTEGER NOT NULL,
    capacidad INTEGER NOT NULL
);

CREATE TABLE funciones (
    id SERIAL PRIMARY KEY,
    pelicula_id INTEGER REFERENCES peliculas(id) ON DELETE CASCADE,
    sala_id INTEGER REFERENCES salas(id) ON DELETE CASCADE,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    precio NUMERIC(5, 2)
);

CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    telefono VARCHAR(20)
);

CREATE TABLE reservas (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER REFERENCES usuarios(id) ON DELETE CASCADE,
    funcion_id INTEGER REFERENCES funciones(id) ON DELETE CASCADE,
    cantidad_boletos INTEGER NOT NULL,
    fecha_reserva TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE estadisticas (
    id SERIAL PRIMARY KEY,
    pelicula_id INTEGER REFERENCES peliculas(id) ON DELETE SET NULL,
    funcion_id INTEGER REFERENCES funciones(id) ON DELETE SET NULL,
    reservas_realizadas INTEGER DEFAULT 0,
    ingresos_totales NUMERIC(10, 2) DEFAULT 0.00
);

CREATE TABLE factura (
    id SERIAL PRIMARY KEY,
    reserva_id INTEGER REFERENCES reservas(id) ON DELETE CASCADE,
    monto_total NUMERIC(10, 2) NOT NULL,
    fecha_factura TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

GRANT INSERT, SELECT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO yider;
