CREATE DATABASE aldebaran;
\c aldebaran;

CREATE TABLE dueno (
    id SERIAL PRIMARY KEY,       
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE    
);

CREATE TABLE coche (
    id SERIAL PRIMARY KEY,       
    marca VARCHAR(50) NOT NULL,  
    modelo VARCHAR(50) NOT NULL, 
    matricula VARCHAR(20) UNIQUE,
    dueno_id INT REFERENCES dueno(id) ON DELETE CASCADE 
);

INSERT INTO dueno (nombre, email) VALUES
('Juan Pérez', 'juan.perez@example.com'),
('María López', 'maria.lopez@example.com');

INSERT INTO coche (marca, modelo, matricula, dueno_id) VALUES
('Toyota', 'Corolla', 'ABC123', 1),
('Ford', 'Focus', 'XYZ789', 2),
('Honda', 'Civic', 'DEF456', 1);