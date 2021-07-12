drop table if exists authors;

create table authors
(
    id      int auto_increment primary key,
    name    varchar(250 char) not null,
    surname varchar(250 char) not null
);

drop table if exists books;

create table books
(
    id        int auto_increment primary key,
    author_id int not null,
    title     varchar(250 char) not null,
    price_old varchar(250 char) default null,
    price     varchar(250 char) default  null
);

alter table books add foreign key (author_id) references authors(id);