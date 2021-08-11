create table purchase_order (
    id bigint not null auto_increment,
    sub_total decimal(10,2) not null,
    total_value decimal(10,2) not null,
    freigh_rate decimal(10,2) not null,

    user_client_id bigint not null,
    payment_way_id bigint not null,
    restaurant_id bigint not null,

    registration_date datetime(6) not null,
    confirmation_date datetime(6),
    cancellation_date datetime(6),
    delivery_date datetime(6),

    status varchar(10) not null,

    address_city_id bigint(20) not null,
    address_cep varchar(9) not null,
    address_complement varchar(60),
    address_district varchar(60) not null,
    address_number varchar(20) not null,
    address_street varchar(100) not null,

    primary key (id),
    constraint fk_purchase_order_address_city foreign key (address_city_id) references city (id),
    constraint fk_purchase_order_user_client foreign key (user_client_id) references user (id),
    constraint fk_purchase_order_payment_way foreign key (payment_way_id) references payment_way (id),
    constraint fk_purchase_order_restaurant foreign key (restaurant_id) references restaurant (id)

) engine=InnoDB default charset=utf8;

create table item_order (
	id bigint not null auto_increment,
	observation varchar(255),
	quantity smallint(6) not null,
	total_price decimal(10,2) not null,
	unit_price decimal(10,2) not null,

    product_id bigint not null,
	purchase_order_id bigint not null,

	primary key (id),
    unique key uk_item_order_product (purchase_order_id, product_id),

    constraint fk_item_order_product foreign key (product_id) references product (id),
	constraint fk_item_order_purchase_order foreign key (purchase_order_id) references purchase_order (id)
) engine=InnoDB default charset=utf8;