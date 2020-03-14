DROP SCHEMA IF EXISTS `estee-na-local`;
CREATE SCHEMA IF NOT EXISTS `estee-na-local`
  DEFAULT CHARACTER SET utf8;
USE `estee-na-local`;

SET SQL_MODE = 'ALLOW_INVALID_DATES';


CREATE TABLE consumer_metric (
	consumer_metric_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	consumer_id INT,
	metric_cd varchar(30),
	metric_value varchar(100),
	create_dt timestamp ,
	create_user varchar(50) ,
	update_dt timestamp ,
	update_user varchar(50)
);

CREATE TABLE consumer_metric_lkup (
	consumer_metric_lkup_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	metric_cd varchar(30),
	description varchar(200),
	event_name varchar(50),
	execution_type varchar(10),
	frequency varchar(5),
	metric_config varchar(1000),

	create_dt timestamp ,
	create_user varchar(50) ,
	update_dt timestamp ,
	update_user varchar(50)
);

CREATE TABLE topic_lkup (
	topic_lkup_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	topic_name varchar(40),
	processor_type varchar(10),
	message_type varchar(10),
	brand_cd varchar(10),
	event_name varchar(40),
	class_name varchar(50),

	create_dt timestamp ,
	create_user varchar(50) ,
	update_dt timestamp ,
	update_user varchar(50)
);