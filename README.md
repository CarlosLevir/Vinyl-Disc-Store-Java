# jsf-crud-exemple
Exemplo de CRUD JSF 2.2 / MySQL / Bootstrap css

Lembro que no projeto tem que usar a api JSF 2.2.9 e o conector mysql java "mysql-connector-java-5.1.18.jar"

Inicie o MySQL
Use os comandos abaixo para criar o banco e a tabela de usuários.
Importante não mudar a senha do banco pois o projeto usa o padrão usuário: root e senha: (vazio).

//cria o banco de dados com nome User 

create database User;

//sobe até o banco User

use User;

//Criação da tabela users com os campos nescessários 

create table users(id int not null primary key auto_increment, name varchar(100), email varchar(50), password varchar(20), gender varchar(1), address text);

//Conferir se a estrutura da tabela ficou correta.

 # Nome	            Tipo	    Agrupamento (Collation)	Atributos	Nulo	Predefinido	Comentários
	1	idPrimária	  int(11)			  Não	None		      AUTO_INCREMENT	 Muda Muda	 Elimina Elimina	
	2	name	        varchar(100)	latin1_swedish_ci		Sim	NULL			 Muda Muda	 Elimina Elimina	
	3	email	        varchar(50)	  latin1_swedish_ci		Sim	NULL			 Muda Muda	 Elimina Elimina	
	4	password	    varchar(20)	  latin1_swedish_ci		Sim	NULL			 Muda Muda	 Elimina Elimina	
	5	gender	      varchar(1)	  latin1_swedish_ci		Sim	NULL			 Muda Muda	 Elimina Elimina	
	6	address	      text	        latin1_swedish_ci		Sim	NULL			 Muda Muda	 Elimina Elimina	
