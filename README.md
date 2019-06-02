# Vinyl-Disc-Store-Java

- [Vinyl-Disc-Store-Java](#vinyl-disc-store-java)
  - [Instalação](#instalação)
    - [Criação do banco de dados com nome store](#criação-do-banco-de-dados-com-nome-store)
    - [Criação das tabelas no banco store](#criação-das-tabelas-no-banco-store)
    - [Conectando ao banco de dados](#conectando-ao-banco-de-dados)

CRUD de uma loja de discos de vinil, utilizando JAVA, JSF, MySQL, JDBC e BOOTSTRAP.

## Instalação

Use os comandos abaixo para montar o ambiente.

### Criação do banco de dados com o nome store

```sh
CREATE DATABASE store;
```

### Criação das tabelas no banco store

Para criar as tabelas, utilize o arquivo `Dump20190602.sql` deixado na raiz do projeto, através do import que o SGBD oferece.

### Conectando ao banco de dados

Para conectar ao banco, é necessário alterar o usuário e senhas no arquivo `ConnectionBean.java`, que pode ser encontrado dentro da pasta `src`.  
Na linha 14, mude o `user` e `password` para o usuário e senha do seu banco.

Atenção: É recomendado que o usuário escolhido possua todos os privilégios concedidos, para evitar eventuais problemas.
