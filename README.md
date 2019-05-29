# Vinyl-Disc-Store-Java

Exemplo de CRUD JSF 2.2.19 / MySQL / Bootstrap css

Lembro que no projeto tem que usar a api JSF 2.2.9 e o conector mysql java "mysql-connector-java-5.1.47.jar"

Inicie o MySQL
Use os comandos abaixo para criar o banco e a tabela de clientes.
## Criação do banco de dados com nome Store

```sh
CREATE DATABASE Store;
```

## Usando o banco Store

```sh
USE Store;
```

## Criação da tabela clientes com os campos nescessários 

create table clients(id int not null primary key auto_increment, name varchar(100), email varchar(50), address text);

//Conferir se a estrutura da tabela ficou correta.

 # Nome	            Tipo	    Agrupamento (Collation)	Atributos	Nulo	Predefinido	Comentários
	1	idPrimária	  int(11)			  Não	None		      AUTO_INCREMENT	 Muda Muda	 Elimina Elimina	
	2	name	        varchar(100)	latin1_swedish_ci		Sim	NULL			 Muda Muda	 Elimina Elimina	
	3	email	        varchar(50)	  latin1_swedish_ci		Sim	NULL			 Muda Muda	 Elimina Elimina	
	4	password	    varchar(20)	  latin1_swedish_ci		Sim	NULL			 Muda Muda	 Elimina Elimina	
	5	gender	      varchar(1)	  latin1_swedish_ci		Sim	NULL			 Muda Muda	 Elimina Elimina	
	6	address	      text	        latin1_swedish_ci		Sim	NULL			 Muda Muda	 Elimina Elimina	
