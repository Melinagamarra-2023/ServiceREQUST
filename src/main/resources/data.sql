-- Crear la tabla Institute
CREATE TABLE institute (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cuise VARCHAR(255) UNIQUE NOT NULL,
    domain VARCHAR(255),
    phone VARCHAR(50),
    mail VARCHAR(255),
    enabled BOOLEAN
);

-- Insertar datos en Institute
INSERT INTO institute (cuise, domain, phone, mail, enabled)
VALUES
('CUIS001', 'example.com', '1234567890', 'contact@example.com', TRUE),
('CUIS002', 'sample.org', '0987654321', 'info@sample.org', TRUE);

-- Crear la tabla Technicians
CREATE TABLE technicians (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cuit VARCHAR(20) UNIQUE NOT NULL,
    institute_id BIGINT,
    name VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    mail VARCHAR(100),
    enabled BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (institute_id) REFERENCES institute(id)
);

-- Insertar datos en Technicians
INSERT INTO technicians (cuit, institute_id, name, lastname, phone, mail, enabled)
VALUES
('20-12345678-9', 1, 'Juan', 'Pérez', '123456789', 'juan.perez@example.com', TRUE),
('20-87654321-0', 2, 'María', 'Gómez', '987654321', 'maria.gomez@example.com', TRUE),
('23-11223344-5', 1, 'Carlos', 'Rodríguez', '456789123', 'carlos.rodriguez@example.com', FALSE);
