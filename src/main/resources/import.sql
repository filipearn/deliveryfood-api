insert into kitchen (id, name) values (1, 'Tilandesa');
insert into kitchen (id, name) values (2, 'Indiana');
insert into restaurant (name, freigh_rate, kitchen_id) values ('Thai Gourmet', 10, 1);
insert into restaurant (name, freigh_rate, kitchen_id) values ('Thai Delivery', 9.50, 1);
insert into restaurant (name, freigh_rate, kitchen_id) values ('Tuk Tuk Comida Indiana', 15, 2);

insert into state (id, name) values (1, 'Minas Gerais');
insert into state (id, name) values (2, 'São Paulo');
insert into state (id, name) values (3, 'Ceará');

insert into city (id, name, state_id) values (1, 'Uberlândia', 1);
insert into city (id, name, state_id) values (2, 'Belo Horizonte', 1);
insert into city (id, name, state_id) values (3, 'São Paulo', 2);
insert into city (id, name, state_id) values (4, 'Campinas', 2);
insert into city (id, name, state_id) values (5, 'Fortaleza', 3);

insert into payment_way (id, description) values (1, 'Cartão de crédito');
insert into payment_way (id, description) values (2, 'Cartão de débito');
insert into payment_way (id, description) values (3, 'Dinheiro');

insert into permission (id, name, description) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permission (id, name, description) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');
    