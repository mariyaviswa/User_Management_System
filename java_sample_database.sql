create database dbviswa;
use dbviswa;
Drop table users;
create table users(
ID int primary key auto_increment,
NAME varchar(30),
AGE int,
CITY varchar(50)
);
select * from users;



