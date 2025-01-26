-- order table
create table order_service.order_seq (next_val bigint);
insert into order_seq values ( 1 );

CREATE TABLE IF NOT EXISTS order_service.order (
    id BIGINT PRIMARY KEY,
    order_id VARCHAR(20) NOT NULL,
    sku_code VARCHAR(20) NOT NULL,
    quantity BIGINT NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);