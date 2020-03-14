CREATE DATABASE elcna;


use elcna;


create table consumer(
  consumer_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  elc_master_id varchar(256) ,
  consumer_type varchar(10) ,
  brand_id INT ,
  market_id INT ,
  salutation varchar(50) ,
  first_name varchar(50) ,
  middle_name varchar(50) ,
  last_name varchar(50) ,
  suffix varchar(50) ,
  gender varchar(10) ,
  full_name varchar(100) ,
  birthday_day INT ,
  birthday_month INT ,
  primary_addr_1 varchar(250) ,
  primary_addr_2 varchar(250) ,
  primary_addr_3 varchar(250) ,
  primary_addr_4 varchar(250) ,
  primary_addr_city varchar(250) ,
  primary_addr_state_province varchar(250) ,
  primary_addr_postal_cd varchar(250) ,
  primary_addr_country varchar(250) ,
  primary_phone varchar(250) ,
  primary_email varchar(250) ,
  primary_phone_type varchar(10) ,
  dm_contactable_ind varchar(2) ,
  email_contactable_ind varchar(2) ,
  phone_contactable_ind varchar(2) ,
  opt_in_status varchar(10) ,
  opt_in_date timestamp ,
  status_cd varchar(10) ,
  reason_cd varchar(10) ,
  registration_date timestamp ,
  registration_touch_point_id INT ,
  last_update_touch_point_id INT ,
  create_source_system_id INT ,
  create_dt timestamp ,
  create_user varchar(50) ,
  update_source_system_id INT ,
  update_dt timestamp ,
  update_user varchar(50)
);


create table transaction_header(
  transaction_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  brand_id INT ,
  consumer_id INT ,
  touchpoint_id INT ,
  associate_id INT ,
  source_system_id INT ,
  transaction_date timestamp ,
  total_item_qty INT ,
  net_item_qty INT ,
  currency_cd varchar(10) ,
  gross_revenue_wout_tax decimal(10,2) ,
  gross_revenue_w_tax decimal(10,2) ,
  tax_amt decimal(10,2) ,
  net_revenue_wout_tax decimal(10,2) ,
  net_revenue_w_tax decimal(10,2) ,
  usd_gross_revenue_wout_tax decimal(10,2) ,
  usd_gross_revenue_w_tax decimal(10,2) ,
  usd_conversion_rate decimal(19,9) ,
  usd_net_revenue_wout_tax decimal(10,2) ,
  usd_net_revenue_w_tax decimal(10,2) ,
  ticket_num varchar(20) ,
  delivery_date timestamp ,
  source_consumer_id varchar(100) ,
  source_associate_id varchar(100) ,
  channel_id INT ,
  status_cd varchar(10) ,
  reason_cd varchar(10) ,
  create_dt timestamp ,
  create_user varchar(50) ,
  update_dt timestamp ,
  update_user varchar(50)
);


create table transaction_detail(
  transaction_detail_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  transaction_id INT ,
  trans_type varchar(20) ,
  line_num INT ,
  product_id INT ,
  product_sku varchar(100) ,
  product_upc varchar(100) ,
  quantity INT ,
  unit_price decimal(10,2) ,
  tax_amt decimal(10,2) ,
  gross_revenue decimal(10,2) ,
  net_revenue decimal(10,2) ,
  discount_amt decimal(10,2) ,
  status_cd varchar(10) ,
  reason_cd varchar(10) ,
  create_dt timestamp ,
  create_user varchar(50) ,
  update_dt timestamp ,
  update_user varchar(50)
);

select * from transaction_detail;

select * from transaction_header;

select * from consumer;
