drop database if exists scheduler_test;
create database scheduler_test;
use scheduler_test;

create table app_user (
user_id int primary key auto_increment,
first_name varchar(50) not null,
last_name varchar(50) not null,
bio varchar(300) null,
url_image varchar(512) null,
phone varchar(20) null,
email varchar(250) not null,
`password` varchar(250) not null,
user_type varchar(20) not null,
enabled boolean not null default true
);

create table service (
service_id int primary key auto_increment,
service_name varchar(50) not null,
service_description varchar(150),
duration int not null,
price decimal(10,2) not null,
enabled boolean not null default true
);

create table appointment (
appointment_id int primary key auto_increment,
start_time timestamp not null,
end_time timestamp not null,
employee_id int not null,
employee_name varchar(20),
customer_id int,
customer_name varchar(20),
service_id int,
service_name varchar(250),
constraint fk_appointment_employee_id
foreign key (employee_id)
references app_user(user_id),
constraint fk_appointment_customer_id
foreign key (customer_id)
references app_user(user_id),
constraint fk_appointment_service_id
foreign key (service_id)
references service(service_id)
);

delimiter //
create procedure set_known_good_state()
begin
delete from appointment;
alter table appointment auto_increment = 1;
delete from app_user;
alter table app_user auto_increment = 1;
delete from service;
alter table service auto_increment = 1;


-- data
insert into app_user 
(first_name, last_name, bio, url_image, phone, email, `password`, user_type)
values
('dummy', 'dummy', null, null, 'dummy phone', 'dummy mail', 'some password', 'customer'),
('Adam', 'West', 'big guy from a small town', 'https://media.istockphoto.com/vectors/default-avatar-photo-placeholder-icon-grey-profile-picture-business-vector-id1327592449?k=20&m=1327592449&s=612x612&w=0&h=6yFQPGaxmMLgoEKibnVSRIEnnBgelAeIAf8FqpLBNww=', null, 'something1@abc', '$2a$10$jILaLNHesmYTMJEnLojVUeEcKKo4FEXmjwRkiPLecFRFX9MGWsDqu', 'employee'),
('Bryce', 'Banks', 'expert at everything', 'https://media.istockphoto.com/vectors/default-avatar-photo-placeholder-icon-grey-profile-picture-business-vector-id1327592449?k=20&m=1327592449&s=612x612&w=0&h=6yFQPGaxmMLgoEKibnVSRIEnnBgelAeIAf8FqpLBNww=', null, 'dummy@123', '$2a$10$yv0wxXG1GbBM4wPYkwWgoe8KVLTDWlP/0eSwH/Z3oVYlRXM.GLwQW', 'employee'),
('Tom', 'Young', 'something interetsing and funny', 'https://media.istockphoto.com/vectors/default-avatar-photo-placeholder-icon-grey-profile-picture-business-vector-id1327592449?k=20&m=1327592449&s=612x612&w=0&h=6yFQPGaxmMLgoEKibnVSRIEnnBgelAeIAf8FqpLBNww=', null, 'tommy@hgh', '$2a$10$vMzxnsajgH2JDzDtU3qcgejAzxm6QXQ5WqWvl/BSfyPa6noFVJSoy', 'employee'),
('Jerry', 'Smith', 'I’m a social media guru.', 'https://media.istockphoto.com/vectors/default-avatar-photo-placeholder-icon-grey-profile-picture-business-vector-id1327592449?k=20&m=1327592449&s=612x612&w=0&h=6yFQPGaxmMLgoEKibnVSRIEnnBgelAeIAf8FqpLBNww=', null, 'joey@php', '$2a$10$A5a2JalL3cbfmPNUI7BSMevSnO.5zvPVBK93xo1CCr1HxMg7uDnty', 'employee'),
('Greg', 'Anthony', null, null, '202-555-6677', 'gregory@gg.com', '$2a$10$4kzB8BDxvONpPLDl1I6edO0evTLSmRT8hj6btDU9G2AuYkEk2Cfim', 'customer'),
('Hank', 'Hill', null, null, '202-555-1234', 'hankh@abc.com', '$2a$10$gHV4PZcdPfdnKhoxs67GQeXUYutlzXRb30Fj7h8sV6JqOt5qcopBe', 'customer'),
('Joe', 'Clark' , null, null, '202-555-9922', 'joes@joe.net', '$2a$10$/vSnHHqhHyeNyZEkJx3PHOenx.MZBAhbz5ybPODor0APgB5tW/wXa', 'customer'),
('Strict', 'Admin' , null, null, null, 'admin@admin', '$2a$10$gOGI22XbyXG7RSfqiWdCQexekBI7VgB7bmN8nBubhReKXC7wRoE56', 'admin');

insert into service
(service_name, service_description, duration, price)
values
('dummy', 'dummy', 10, 10.00),
('haircut', 'regular hair cut no shave', 60, 20.00),
('shave', 'single blade shave', 30, 10.00),
('dye', 'dye (red, green, blue)', 90, 25.00);

insert into appointment
(start_time, end_time, employee_id, employee_name, customer_id, customer_name, service_id, service_name)
values
('2022-11-11 09:00:00', '2022-11-11 10:00:00', 2, "Adam", 6, "Greg", 2, "haircut"),
('2022-11-15 11:00:00', '2022-11-15 12:30:00', 3, "Bryce", 6, "Greg", 3, "shave"),
('2022-11-17 10:00:00', '2022-11-17 10:30:00', 2, "Adam", 7, "Hank", 2, "haircut");

end //
-- Change the statement terminator back to the original.
delimiter ;