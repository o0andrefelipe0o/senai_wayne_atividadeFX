DROP DATABASE wayne_enterprises_db;

CREATE DATABASE IF NOT EXISTS wayne_enterprises_db;

USE wayne_enterprises_db;

CREATE TABLE IF NOT EXISTS usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    tipo ENUM('admin', 'normal') NOT NULL DEFAULT 'normal'
);

CREATE TABLE IF NOT EXISTS funcionarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_completo VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    cargo VARCHAR(100),
    departamento VARCHAR(100),
    email VARCHAR(100) NOT NULL UNIQUE,
    data_admissao DATE NOT NULL,
    data_nascimento DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS ferias (
    id INT PRIMARY KEY AUTO_INCREMENT,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    observacao TEXT,
    funcionario_id INT NOT NULL,
    FOREIGN KEY (funcionario_id) REFERENCES funcionarios(id) ON DELETE CASCADE
);

INSERT INTO usuario (usuario, senha, tipo)
VALUES ('admin', 'admin','admin');




