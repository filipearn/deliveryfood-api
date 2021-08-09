insert into kitchen (id, name) values (1, 'Tilandesa');
insert into kitchen (id, name) values (2, 'Indiana');

insert into state (id, name) values (1, 'Minas Gerais');
insert into state (id, name) values (2, 'São Paulo');
insert into state (id, name) values (3, 'Ceará');

insert into city (id, name, state_id) values (1, 'Uberlândia', 1);
insert into city (id, name, state_id) values (2, 'Belo Horizonte', 1);
insert into city (id, name, state_id) values (3, 'São Paulo', 2);
insert into city (id, name, state_id) values (4, 'Campinas', 2);
insert into city (id, name, state_id) values (5, 'Fortaleza', 3);

insert into restaurant (id, name, freigh_rate, kitchen_id, address_city_id, address_cep, address_street, address_number, address_district, registration_date, update_date) values (1, 'Thai Gourmet', 10, 1, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro', utc_timestamp, utc_timestamp);
insert into restaurant (name, freigh_rate, kitchen_id, registration_date, update_date) values ('Thai Delivery', 0, 1, utc_timestamp, utc_timestamp);
insert into restaurant (name, freigh_rate, kitchen_id, registration_date, update_date) values ('Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp);

insert into payment_way (id, description) values (1, 'Cartão de crédito');
insert into payment_way (id, description) values (2, 'Cartão de débito');
insert into payment_way (id, description) values (3, 'Dinheiro');

insert into permission (id, name, description) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permission (id, name, description) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into restaurant_payment_way (restaurant_id, payment_way_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);