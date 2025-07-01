CREATE DATABASE IF NOT EXISTS wayne_enterprises_db;

USE wayne_enterprises_db;

CREATE TABLE IF NOT EXISTS funcionarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    nome_completo VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    cargo VARCHAR(100),
    departamento VARCHAR(100),
    email VARCHAR(100) NOT NULL UNIQUE,
    data_admissao DATE NOT NULL,
    data_nascimento DATE NOT NULL
);

DROP USER IF EXISTS 'admin'@'localhost';

CREATE USER IF NOT EXISTS 'admin'@'localhost' IDENTIFIED BY '1234567';

GRANT ALL PRIVILEGES ON sistema_login.* TO 'admin'@'localhost';

FLUSH PRIVILEGES;

SELECT*FROM funcionarios;
