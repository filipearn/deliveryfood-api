create table payment_way (
	id bigint not null auto_increment,
	description varchar(60) not null,

    primary key (id)
) engine=InnoDB default charset=utf8;

create table permission (
    id bigint not null auto_increment,
    description varchar(60) not null,
    name varchar(100) not null,

    primary key (id)
) engine=InnoDB default charset=utf8;

create table product (
    id bigint not null auto_increment,
    active tinyint(1) not null,
    description text not null,
    name varchar(80) not null,
    price decimal(10,2) not null,
    restaurant_id bigint not null,

    primary key (id)
) engine=InnoDB default charset=utf8;

create table restaurant (

id bigint not null auto_increment,
active tinyint(1),
freigh_rate decimal(10,2),
name varchar(60)  not null,
registration_date datetime not null,
update_date datetime not null,
kitchen_id bigint not null,

address_city_id bigint,
address_cep varchar(9),
address_complement varchar(60),
address_district varchar(100),
address_number varchar(20),
address_street varchar(100),

primary key (id)
) engine=InnoDB default charset=utf8;

create table restaurant_payment_way (
restaurant_id bigint not null,
payment_way_id bigint not null,

primary key (restaurant_id, payment_way_id)
) engine=InnoDB default charset=utf8;

create table team (
    id bigint not null auto_increment,
    name varchar(60) not null,

    primary key (id)
) engine=InnoDB default charset=utf8;

create table team_permission (
team_id bigint not null,
permission_id bigint not null,

primary key (team_id, permission_id)
) engine=InnoDB default charset=utf8;

create table user (
id bigint not null auto_increment,
email varchar(255) not null,
name varchar(80) not null,
password varchar(255) not null,
registration_date datetime not null,

primary key (id)
) engine=InnoDB default charset=utf8;

create table user_team (
user_id bigint not null,
team_id bigint not null,

primary key (user_id, team_id)
) engine=InnoDB default charset=utf8;

alter table product add constraint fk_product_restaurant foreign key (restaurant_id) references restaurant (id);
alter table restaurant add constraint fk_restaurant_city foreign key (address_city_id) references city (id);
alter table restaurant add constraint fk_restaurant_kitchen foreign key (kitchen_id) references kitchen (id);
alter table restaurant_payment_way add constraint fk_rest_payment_way_payment_way foreign key (payment_way_id) references payment_way (id);
alter table restaurant_payment_way add constraint fk_rest_payment_way_restaurtant foreign key (restaurant_id) references restaurant (id);
alter table team_permission add constraint fk_team_permission_permission foreign key (permission_id) references permission (id);
alter table team_permission add constraint fk_team_permission_team foreign key (team_id) references team (id);
alter table user_team add constraint fk_user_team_team foreign key (team_id) references team (id);
alter table user_team add constraint fk_user_team_user foreign key (user_id) references user (id);