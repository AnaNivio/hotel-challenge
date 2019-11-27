create database hotelChallenge;
use hotelChallenge;

create table rooms(
	room_id int unsigned not null auto_increment, 
    beds_amount int unsigned not null,
    price float unsigned not null,
    avaiable Boolean,
    occupied Boolean,
    reason varchar(50),
    services varchar(250)

	constraint pk_rooms primary key(room_id)

);

create table guests(
    guest_id int unsigned not null auto_increment, 
    name varchar(50) not null,
    surname varchar(50) not null,
    dni varchar(50) not null,
    number varchar(50),
    city varchar(50),

    constraint pk_guests primary key(guest_id)

);

create table reservations(
    reservation_id int unsigned not null auto_increment, 
    check_in datetime not null,
    check_out datetime not null,
    guests_number int unsigned not null,
	room_id int unsigned not null, 
    guest_id int unsigned not null,

    constraint pk_reservations primary key(reservation_id),
	constraint fk_reservation_room foreign key(room_id) 
	references rooms(room_id) ON DELETE CASCADE
    ON UPDATE CASCADE,
    constraint fk_reservation_guest foreign key(guest_id) 
	references guests(guest_id)ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- see if this needs to have specified on delete and update cascade like in models
    -- ON DELETE CASCADE
    -- ON UPDATE CASCADE
