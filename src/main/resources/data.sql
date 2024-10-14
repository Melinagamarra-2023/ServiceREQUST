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
INSERT INTO institute (cuise, domain, phone, mail, enabled) VALUES ('CUIS001', 'example.com', '1234567890', 'contact@example.com', TRUE);
INSERT INTO institute (cuise, domain, phone, mail, enabled) VALUES ('CUIS002', 'sample.org', '0987654321', 'info@sample.org', TRUE);
