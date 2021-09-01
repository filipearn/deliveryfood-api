alter table purchase_order add code varchar(36) not null after id;
update purchase_order set code = uuid();
alter table purchase_order add constraint uk_purchase_order_code unique (code);