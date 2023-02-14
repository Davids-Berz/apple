insert into clientes (nombre, apellido, email, create_at, foto) values ('david', 'saldivar', 'david@gmail.com', '2023-01-18','');
insert into clientes (nombre, apellido, email, create_at, foto) values ('Dianey', 'Tapia', 'dt@gmail.com', '2022-01-18','');
insert into clientes (nombre, apellido, email, create_at, foto) values ('Juan', 'Hernan', 'jh@gmail.com', '2022-11-10','');

insert into productos(nombre, precio, create_at) values ('Sony Camara Digital DSC-W320', 23990,'2023-01-19');
insert into productos(nombre, precio, create_at) values ('Apple iphone 14 pro max', 34499, '2023-04-04');
insert into productos(nombre, precio, create_at) values ('Whirlpool WM1211D Horno de Microondas', 3499, '2021-11-12');
insert into productos(nombre, precio, create_at) values ('Switch Neon 32GB Version 1.1', 6347, '2021-11-12');
insert into productos(nombre, precio, create_at) values ('Nintendo Switch Lite Hand-Held', 5348, '2022-06-01');


INSERT INTO users (username, PASSWORD, ENABLE) VALUES ('David','$2a$10$HjtXQMFdLVk88RFCpSllXeU0VuG6Tb8RKbnFy6rR2/JjFmKCvzBgu', 1);
INSERT INTO users (username, PASSWORD, ENABLE) VALUES ('Luna','$2a$10$B1.4bIZjdsFlaq6g4chL8.b9Vla4drATu7QMfc42q/s3Sy6wkNx1G', 1);

INSERT INTO authorities (user_id,authority) VALUES (2, 'ROLE_USER');
INSERT INTO authorities (user_id,authority) VALUES (1, 'ROLE_USER');
INSERT INTO authorities (user_id,authority) VALUES (1, 'ROLE_ADMIN');