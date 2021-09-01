create table restaurant_user (
restaurant_id bigint not null,
user_id bigint not null,

primary key (restaurant_id, user_id)
) engine=InnoDB default charset=utf8;