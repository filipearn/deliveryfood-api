set foreign_key_checks = 0;

delete from city;
delete from kitchen;
delete from state;
delete from payment_way;
delete from team;
delete from team_permission;
delete from permission;
delete from product;
delete from restaurant;
delete from restaurant_payment_way;
delete from user;
delete from user_team;
delete from restaurant_user;

set foreign_key_checks = 1;

alter table city auto_increment = 1;
alter table kitchen auto_increment = 1;
alter table state auto_increment = 1;
alter table payment_way auto_increment = 1;
alter table team auto_increment = 1;
alter table permission auto_increment = 1;
alter table product auto_increment = 1;
alter table restaurant auto_increment = 1;
alter table user auto_increment = 1;


insert into kitchen (id, name) values (1, 'Tailandesa');
insert into kitchen (id, name) values (2, 'Indiana');
insert into kitchen (id, name) values (3, 'Argentina');
insert into kitchen (id, name) values (4, 'Brasileira');

insert into state (id, name) values (1, 'Minas Gerais');
insert into state (id, name) values (2, 'São Paulo');
insert into state (id, name) values (3, 'Ceará');

insert into city (id, name, state_id) values (1, 'Uberlândia', 1);
insert into city (id, name, state_id) values (2, 'Belo Horizonte', 1);
insert into city (id, name, state_id) values (3, 'São Paulo', 2);
insert into city (id, name, state_id) values (4, 'Campinas', 2);
insert into city (id, name, state_id) values (5, 'Fortaleza', 3);

insert into restaurant (id, name, freight_rate, kitchen_id, address_city_id, address_cep, address_street, address_number, address_district, registration_date, update_date, active, opened) values (1, 'Thai Gourmet', 10, 1, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro', utc_timestamp, utc_timestamp, true, false);
insert into restaurant (id, name, freight_rate, kitchen_id, registration_date, update_date, active, opened) values (2, 'Thai Delivery', 0, 1, utc_timestamp, utc_timestamp, true, false);
insert into restaurant (id, name, freight_rate, kitchen_id, registration_date, update_date, active, opened) values (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp, true, false);
insert into restaurant (id, name, freight_rate, kitchen_id, registration_date, update_date, active, opened) values (4, 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp, true, false);
insert into restaurant (id, name, freight_rate, kitchen_id, registration_date, update_date, active, opened) values (5, 'Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp, true, false);
insert into restaurant (id, name, freight_rate, kitchen_id, registration_date, update_date, active, opened) values (6, 'Bar da Maria', 6, 4, utc_timestamp, utc_timestamp, true, false);

insert into payment_way (id, description) values (1, 'Cartão de crédito');
insert into payment_way (id, description) values (2, 'Cartão de débito');
insert into payment_way (id, description) values (3, 'Dinheiro');

insert into permission (id, name, description) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permission (id, name, description) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into restaurant_payment_way (restaurant_id, payment_way_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert into product (name, description, price, active, restaurant_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
insert into product (name, description, price, active, restaurant_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);

insert into product (name, description, price, active, restaurant_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);
insert into product (name, description, price, active, restaurant_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert into product (name, description, price, active, restaurant_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);
insert into product (name, description, price, active, restaurant_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert into product (name, description, price, active, restaurant_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);
insert into product (name, description, price, active, restaurant_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);
insert into product (name, description, price, active, restaurant_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

insert into user (name, email, password, registration_date) values ('Filipe Nepomuceno', 'filipearn@yahoo.com.br', '123', utc_timestamp);
insert into user (name, email, password, registration_date) values ('Jéssica Pessoa', 'jessicasereia@gmail.com.br', '123', utc_timestamp);
insert into user (name, email, password, registration_date) values ('Marcus Vinícius', 'marcus@yahoo.com.br', '123', utc_timestamp);
insert into user (name, email, password, registration_date) values ('Leonardo Moyle', 'leo@yahoo.com.br', '123', utc_timestamp);

insert into team (name) values ('Gerente'), ('Vendedor'), ('Secretária'), ('Cadastrador');

insert into team_permission (team_id, permission_id) values (1, 1), (1, 2), (2, 1), (2, 2), (3, 1);

insert into user_team (user_id, team_id) values (1, 1), (1, 2), (2, 2);

insert into restaurant_user (restaurant_id, user_id) values (1,2), (1,3);