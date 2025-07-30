CREATE TABLE topicos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    mensagem TEXT NOT NULL,
    autor VARCHAR(255) NOT NULL,
    curso VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    data_criacao DATETIME NOT NULL,
    ativo BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);
