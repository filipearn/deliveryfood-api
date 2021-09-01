alter table payment_way add update_date datetime null;

update payment_way set update_date = utc_timestamp;

alter table payment_way modify update_date datetime not null;