create user pgpadrao password 'pgpadrao123';

drop table if exists tbl_comentario;
drop table if exists tbl_usuario;

create table if not exists tbl_usuario(
	login varchar(50) not null primary key,
	senha varchar(100) not null
);

create table if not exists tbl_comentario(
	id serial not null primary key,
	comentario text,
	datahora timestamp,
	usuario varchar(50) references tbl_usuario(login)
);

insert into tbl_usuario(login, senha)
	values('daniel','daniel123');