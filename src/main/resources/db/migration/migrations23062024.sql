CREATE
    DATABASE IF NOT EXISTS forumhub;

USE
    forumhub;

CREATE TABLE IF NOT EXISTS Curso
(
    id
              INT
        AUTO_INCREMENT
        PRIMARY
            KEY,
    nome
              VARCHAR(255) NOT NULL,
    categoria VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Perfil
(
    id
        INT
        AUTO_INCREMENT
        PRIMARY
            KEY,
    nome
        VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Usuario
(
    id
           INT
        AUTO_INCREMENT
        PRIMARY
            KEY,
    nome
           VARCHAR(255) NOT NULL,
    email  VARCHAR(255) NOT NULL,
    senha  VARCHAR(255) NOT NULL,
    perfis INT,
    FOREIGN KEY
        (
         perfis
            ) REFERENCES Perfil
        (
         id
            )
);

CREATE TABLE IF NOT EXISTS Topico
(
    id
                INT
        AUTO_INCREMENT
        PRIMARY
            KEY,
    titulo
                VARCHAR(255) NOT NULL,
    mensagem    TEXT         NOT NULL,
    dataCriacao DATETIME     NOT NULL,
    status      VARCHAR(255) NOT NULL,
    autor       INT,
    curso       INT,
    respostas   INT,
    FOREIGN KEY
        (
         autor
            ) REFERENCES Usuario
        (
         id
            ),
    FOREIGN KEY
        (
         curso
            ) REFERENCES Curso
        (
         id
            )
);

CREATE TABLE IF NOT EXISTS Resposta
(
    id
        INT
        AUTO_INCREMENT
        PRIMARY
            KEY,
    mensagem
        TEXT
        NOT
            NULL,
    topico
        INT,
    dataCriacao
        DATETIME
        NOT
            NULL,
    autor
        INT,
    solucao
        BOOLEAN,
    FOREIGN
        KEY
        (
         topico
            ) REFERENCES Topico
        (
         id
            ),
    FOREIGN KEY
        (
         autor
            ) REFERENCES Usuario
        (
         id
            )
);