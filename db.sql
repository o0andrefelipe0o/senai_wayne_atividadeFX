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

INSERT INTO funcionarios (nome_completo, cpf, cargo, departamento, email, data_admissao, data_nascimento) VALUES
('Bruce Wayne', '123.456.789-00', 'CEO', 'Administração', 'bruce@wayneenterprises.com', '2010-05-01', '1972-07-07'),
('Lucius Fox', '321.654.987-00', 'Diretor Técnico', 'Tecnologia', 'lucius@wayneenterprises.com', '2012-08-15', '1965-7-09'),
('Alfred Pennyworth', '987.654.321-00', 'Assistente Pessoal', 'Executivo', 'alfred@wayneenterprises.com', '2000-01-01', '1945-07-01'),
('Selina Kyle', '741.852.963-00', 'Analista de Segurança', 'Segurança', 'selina@wayneenterprises.com', '2018-09-10', '1985-07-30'),
('Barbara Gordon', '852.963.741-00', 'Analista de Dados', 'TI', 'barbara@wayneenterprises.com', '2019-03-22', '1990-10-10'),
('Harvey Dent', '159.357.486-00', 'Jurídico', 'Legal', 'harvey@wayneenterprises.com', '2015-07-19', '1978-01-25'),
('Pamela Isley', '357.159.486-00', 'Pesquisadora', 'P&D', 'pamela@wayneenterprises.com', '2017-04-27', '1984-09-08'),
('Edward Nigma', '753.951.258-00', 'Engenheiro de Software', 'TI', 'edward@wayneenterprises.com', '2020-11-03', '1988-05-05'),
('Dick Grayson', '951.753.852-00', 'Coordenador de Projetos', 'Operações', 'dick@wayneenterprises.com', '2016-06-30', '1992-03-15'),
('Jason Todd', '159.753.456-00', 'Analista de Qualidade', 'Controle', 'jason@wayneenterprises.com', '2021-01-18', '1995-12-01');



