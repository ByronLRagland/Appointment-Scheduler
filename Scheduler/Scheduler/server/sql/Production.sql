drop database if exists scheduler;
create database scheduler;
use scheduler;

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

-- data
insert into app_user 
(first_name, last_name, bio, url_image, phone, email, `password`, user_type)
values
('Terry', 'Hollins', null, null, null, 'thollins@terry.com', 'terry', 'customer'),
('Adam', 'Wilson', 'Been there since a young age, at it for over 16 years now. I’ve earned my place behind the
 chair as much as any other barber out there, so when you come in to see me, you’re guaranteed authentic, precision cuts.',
 'https://images.unsplash.com/photo-1589985502143-057e63eef1c9?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80', null, 'adamw@abc.com', '$2a$10$jILaLNHesmYTMJEnLojVUeEcKKo4FEXmjwRkiPLecFRFX9MGWsDqu', 'employee'),
('Bryce', 'Banks', 'I began trimming my own hair throughout high school and then my class mates in my school restroom.
 There is truly no age for passion to this fine craft & I can testify to that especially to the skin fades.',
 'https://images.unsplash.com/photo-1567894340315-735d7c361db0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=437&q=80', null, 'bbanks@123.net', '$2a$10$yv0wxXG1GbBM4wPYkwWgoe8KVLTDWlP/0eSwH/Z3oVYlRXM.GLwQW', 'employee'),
('Thomas', 'Hernandez', 'From the first time I sat in an old-fashioned barber’s chair, to my first (and every) haircut
 since that day, I’ve been fascinated by the art that is barbering. This is my happy place. Where people make your 
 hair look good and you feel good.', 'https://images.unsplash.com/photo-1596513058260-ac19435ec75a?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80', null, 'tommy@xyz.com', '$2a$10$vMzxnsajgH2JDzDtU3qcgejAzxm6QXQ5WqWvl/BSfyPa6noFVJSoy', 'employee'),
('Jerry', 'Harris', 'I only cut men’s hair… unless you’re into that sort of thing. Then I’d be happy to do it for you as well. Barber by choice, bald by trade.', 'https://images.unsplash.com/photo-1582893561942-d61adcb2e534?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=391&q=80', null, 'jharris@php.com', '$2a$10$A5a2JalL3cbfmPNUI7BSMevSnO.5zvPVBK93xo1CCr1HxMg7uDnty', 'employee'),
('Greg', 'Anthony', 'I love hiking and going for bike rides on the trail.', 'https://images.unsplash.com/photo-1629467057269-83f2bf9095cd?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1021&q=80', '202-555-6677', 'gregory@gg.com', '$2a$10$4kzB8BDxvONpPLDl1I6edO0evTLSmRT8hj6btDU9G2AuYkEk2Cfim', 'customer'),
('Hank', 'Hamilton', 'Guy with a strong desire to travel the world and explore new places.', 'https://images.unsplash.com/photo-1498426317435-c4a0ebd4774f?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1169&q=80', '202-555-1234', 'hankh@abc.com', '$2a$10$gHV4PZcdPfdnKhoxs67GQeXUYutlzXRb30Fj7h8sV6JqOt5qcopBe', 'customer'),
('Joseph', 'Green' , 'Die-hard Dallas Cowboys fan.', 'https://images.unsplash.com/photo-1522529599102-193c0d76b5b6?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80', '202-555-9922', 'joes@joe.net', '$2a$10$/vSnHHqhHyeNyZEkJx3PHOenx.MZBAhbz5ybPODor0APgB5tW/wXa', 'customer'),
('Bryan', 'Miller' , null, 'https://images.unsplash.com/photo-1614890107637-fe96d74acf4b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80', null, 'admin@admin', '$2a$10$gOGI22XbyXG7RSfqiWdCQexekBI7VgB7bmN8nBubhReKXC7wRoE56', 'admin');



insert into service
(service_name, service_description, duration, price, enabled)
values
('Head Shave', '(Bald Shave)', 10, 10.00, false), 
('Haircut', '(Basic Haircut)', 30, 20.00, true),
('Specialty Haircut', '(Mohawk, Fade, etc.)', 60, 25.00, true),
('Haircut + Shave', '(Basic Haircut Plus Shave)', 60, 30.00, true),
('Shave', '(Basic Wet Shave)', 20, 15.00, true),
('Hair Coloring', '(Temproary Hair Dye)', 90, 60.00, true);

insert into appointment
(start_time, end_time, employee_id, employee_name, customer_id, customer_name, service_id, service_name)
values
('2022-11-11 09:00:00', '2022-11-11 10:00:00', 2, "Adam", 6, "Greg", 2, "haircut"),
('2022-11-15 11:00:00', '2022-11-15 12:30:00', 3, "Bryce", 6, "Greg", 3, "shave"),
('2022-11-17 10:00:00', '2022-11-17 10:30:00', 2, "Adam", 7, "Hank", 2, "haircut");

 select * from appointment;
 -- select * from app_user 
-- where user_id = 3
-- and user_type = "employee"

