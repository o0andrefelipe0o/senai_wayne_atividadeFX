# SENAI Centro de Treinamento da Tecnologia da Informação
# Projeto Sistema de Gestão de Funcionários - Wayne Enterprises
[![NPM](https://i.imgur.com/jxmOeKW.png)](https://www.fiemg.com.br/senai/)

Este é o repositório do projeto desenvolvido por uma dupla do **SENAI Centro de Treinamento da Tecnologia da Informação (Belo Horizonte - MG)**.Este projeto consiste em uma aplicação desktop moderna, desenvolvida em JavaFX, para a administração completa de colaboradores, incluindo gerenciamento de dados, controle de férias e geração de relatórios visuais.


## Sobre o progeto
Este sistema foi desenvolvido como um projeto acadêmico por **Vitor Dornelas** e **André Felipe**. O objetivo principal foi aplicar na prática os conhecimentos adquiridos em desenvolvimento de software, interfaces gráficas e banco de dados.
## Principais Funcionalidades

**Autenticação Segura:**
- Tela de login com validação de credenciais de usuário.

**Gestão de Funcionários:**

- Cadastro completo de novos colaboradores.

- Atualização de informações cadastrais.

- Exclusão segura de registros com diálogo de confirmação.

**Controle de Férias:**

- Agendamento de períodos de férias vinculado a cada funcionário.

- Visualização e gerenciamento das férias agendadas.

- Agenda para eventos corporativos.

**Relatórios e Dashboards:**

- Gráficos dinâmicos para análise de alocação de funcionários por departamento.

- Relatório visual de períodos de férias.

**Consultas e Filtros:**

Listagem de funcionários e férias com opções de filtragem para facilitar a busca.

**Manutenção do Sistema:**

Funcionalidade para restauração e backup de dados (simulada ou real).



## Começando
Siga estas instruções para obter uma cópia funcional do projeto em sua máquina local para desenvolvimento e testes.

**Pré-requisitos**

Para executar este projeto, você precisará ter instalado em seu ambiente:

- JDK 17 ou superior.

- MySQL Server 8.0 ou superior.

- Uma IDE Java de sua preferência (Ex: IntelliJ IDEA, Eclipse, VS Code com extensões Java).

- Maven ou Gradle (se o projeto utilizar um gerenciador de dependências).
## Installation


**Clone o repositório:**

```javascript
git clone https://github.com/seu-usuario/nome-do-repositorio.git

```

**Configure o Banco de Dados:**

- Abra seu cliente MySQL (Workbench, DBeaver, etc.).

- Execute o script SQL localizado em database/schema.sql (ou o script abaixo) para criar o banco de dados e as tabelas necessárias.

**Configure a Conexão:**

- Dentro do projeto, localize o arquivo de configuração do banco de dados .

- Atualize as credenciais de acesso (URL, usuário e senha) com as suas informações do MySQL.

- Execute a Aplicação:

- Abra o projeto na sua IDE.

- Compile e execute a classe principal que inicia a aplicação JavaFX .

**Acesse o Sistema:**

- Usuário: admin

- Senha: admin


    
##   Estrutura do Banco de Dados


O script abaixo define a estrutura completa do banco de dados wayne_enterprises_db utilizado pelo sistema.

Clique para ver o script SQL
SQL

```
CREATE DATABASE IF NOT EXISTS wayne_enterprises_db;
USE wayne_enterprises_db;

-- Tabela para autenticação de usuários
CREATE TABLE IF NOT EXISTS usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    tipo ENUM('admin', 'normal') NOT NULL DEFAULT 'normal'
);

-- Tabela principal de funcionários
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

-- Tabela para controle de férias
CREATE TABLE IF NOT EXISTS ferias (
    id INT PRIMARY KEY AUTO_INCREMENT,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    observacao TEXT,
    funcionario_id INT NOT NULL,
    FOREIGN KEY (funcionario_id) REFERENCES funcionarios(id) ON DELETE CASCADE
);

-- Inserção do usuário administrador padrão
INSERT INTO usuario (usuario, senha, tipo)
VALUES ('admin', 'admin', 'admin');

```

## License

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.
## Contato

**Vitor Dornelas:** 
- [Link LinkedIn]( https://www.linkedin.com/in/vitor-dornelas-113b442a9/)
- [Link GitHub](https://github.com/VitorDornelas)


**André Felipe:** 
- [Link LinkedIn]( https://www.linkedin.com/in/o0andrefelipe0o/ )
- [Link GitHub](https://github.com/o0andrefelipe0o)

## Agradecimento

**Instrutor**

[Djalma Batista Junior] - ( https://www.linkedin.com/in/djalmabjunior/ )
