create database eazyschooldb;

use eazyschooldb;

CREATE TABLE IF NOT EXISTS `contact_msg`(
`contact_id` int auto_increment primary key,
`name` varchar(100) not null,
`mobile_num` varchar(10) not null,
`email` varchar(100) not null,
`subject` varchar(100) not null,
`message` varchar(500) not null,
`status` varchar(10) not null,
`created_at` timestamp not null,
`created_by` varchar(50) not null,
`updated_at` timestamp default null,
`updated_by` varchar(50) default null
);

create table if not exists `holidays`(
  `day` varchar(20) not null,
  `reason` varchar(100) not null,
  `type` varchar(20) not null,
  `created_at` timestamp not null,
  `created_by` varchar(50) not null,
  `updated_at` timestamp default null,
  `updated_by` varchar(50) default null
);

CREATE TABLE IF NOT EXISTS `person`(
`person_id` int auto_increment primary key,
`name` varchar(100) not null,
`mobile_number` varchar(10) not null,
`email` varchar(100) not null,
`password` varchar(500) not null,
`created_at` timestamp not null,
`created_by` varchar(50) not null,
`updated_at` timestamp default null,
`updated_by` varchar(50) default null
);

CREATE TABLE IF NOT EXISTS `class` (
    `class_id` int not null auto_increment primary key,
    `name` varchar(100) not null,
    `created_at` timestamp not null,
    `created_by` varchar(50) not null,
    `updated_at` timestamp default null,
    `updated_by` varchar(50) default null
);

ALTER TABLE `person`
add column `class_id` int null after `address_id`,
add constraint `fk_class_class_id` foreign key (`class_id`)
references `class`(`class_id`)

CREATE TABLE IF NOT EXISTS `courses` (
  `course_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `fees` varchar(10) NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `updated_at` TIMESTAMP DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
   PRIMARY KEY (`course_id`)
);

CREATE TABLE IF NOT EXISTS `person_courses` (
  `person_id` int NOT NULL,
  `course_id` int NOT NULL,
  FOREIGN KEY (person_id) REFERENCES person(person_id),
  FOREIGN KEY (course_id) REFERENCES courses(course_id),
   PRIMARY KEY (`person_id`,`course_id`)
);